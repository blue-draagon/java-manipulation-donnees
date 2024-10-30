package module;

import entity.Dataset;
import entity.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatasetManipulation {
  public static final String TRANSACTION_DATASET = "TRANSACTION_DATESET";
  public static final String BOOLEEN_DATESET = "BOOLEEN_DATESET";
  public static final String ITEM_DATESET = "ITEM_DATESET";

  // Charger un dataset
  //-------------------------
  public Dataset looadDataset(File file, String datasetType) throws DatasetException {

    if (!file.exists()) {
      throw new DatasetException("Impossible de trouver le fichier : "+ file);
    }

    // Verifier si le type de dataset est correct
    boolean testType1 = !TRANSACTION_DATASET.equals(datasetType);
    boolean testType2 = !BOOLEEN_DATESET.equals(datasetType);
    boolean testType3 = !ITEM_DATESET.equals(datasetType);

    if (testType1 && testType2 && testType3) {
      throw new DatasetException("Merci de renseigner un type de dataset correct");
    }

    Dataset dataset = null;

    try {
      switch (datasetType) {

        case TRANSACTION_DATASET: {
          dataset = this.initTransactionDataset(this.getLines(file));
        }
        break;

        case BOOLEEN_DATESET: {
          dataset = this.initBooleenDataset(this.getLines(file));
        }
        break;

        case ITEM_DATESET: {
          dataset = this.initItemDataset(this.getLines(file));
        }
        break;

      }
    } catch (IOException e) {
      throw new DatasetException("Echec de chargement du fichier", e);
    }

    return dataset;
  }

  public void writeDataset(Dataset dataset, File file) throws DatasetException {
    try {
      BufferedWriter bw;

      if (!file.exists()) {
        bw = new BufferedWriter(new FileWriter(file));
        for (String line : dataset.getLines()) {
          bw.write(line + "\n");
        }
        bw.close();
      }
    } catch (Exception exc) {
      throw new DatasetException("Echec d'ecriture du fichier");
    }
  }

  // Créer un transaction dataset a partir des lignes du fichier
  //--------------------------------------------------------------
  private Dataset initTransactionDataset(List<String> lines) {

    Dataset dataset = new Dataset(lines, TRANSACTION_DATASET);
    List<String> words = new ArrayList<>();

    for (String line : lines) {

      int lineNumber = this.getLineNumber(line);

      for (String s : line.split(",")) {

        if (!isNumeric(s) && !words.contains(s)) {

          words.add(s);
          Word word = new Word(s);
          word.addLine(lineNumber);
          dataset.addWord(word);

        }
      }
    }

    return dataset;
  }

  //  Créer un booleen dataset a partir des lignes du fichier
  //-----------------------------------------------------
  private Dataset initBooleenDataset(List<String> lines) {

    Dataset dataset = new Dataset(lines, BOOLEEN_DATESET);
    dataset.setLineNumber(lines.size()-1);
    List<Word> words = new ArrayList<>();

    // si le dataset est correct
    if (!lines.isEmpty()) {

      // parcour des lignes
      for (int i = 0; i < lines.size(); i++) {

        // reccuperation des mots sur la premiere ligne
        if (i == 0) {
          for (String s : lines.get(i).split(",")) {

            if (!s.matches("TID")) {
              words.add(new Word(s.trim()));
            }
          }
        } else {
          List<String> params = Arrays.asList(lines.get(i).split(","));

          // verifier si le nombre de parametre est egale au nombre de mot plus le numero de ligne
          if (params.size() != (words.size() + 1)) {
            throw new DatasetException("Echec de chargement du dataset");
          }

          // recuperer le numero de ligne (premier parametre
          int lineNumber = this.convertToInt(params.get(0).trim());

          // Parcour de la liste des parametres
          // Si le paramtre vaut T on ajouter le numero de ligne
          // a la liste des lignes du mot
          for (int col = 1; col < params.size(); col++) {

            if (params.get(col).trim().equals("T")) {
              words.get(col - 1).addLine(lineNumber);
            }
          }
        }
      }

      dataset.setWordList(words);
      return dataset;

    } else {
      throw new DatasetException("Ce dataset ne contient aucune ligne");
    }

  }

  // Create Item dataset from list of file lines
  //-----------------------------------------------------
  private Dataset initItemDataset(List<String> lines) {
    return null;
  }

  public Dataset Dataset2ToDataset1(Dataset dataset2) {
    // si le dataset 2 est booleen
    // que la liste de mot n'est vide
    // que le nombre de ligne n'est pas null
    if (!dataset2.getType().equals(BOOLEEN_DATESET) && !dataset2.getWordList().isEmpty() && !(dataset2.getLineNumber() == 0)) {
      throw new DatasetException("Ce dataset n'est pas correct");
    }

    Dataset dataset1 = new Dataset(TRANSACTION_DATASET);
    dataset1.setWordList(dataset2.getWordList());
    dataset1.setLineNumber(dataset2.getLineNumber());

    List<String> lines = new ArrayList<>();

    // Ecrire la liste des lignes
    for (int i = 1; i <= dataset2.getLineNumber(); i++) {
      String line = i + "";
      for (Word word : dataset1.getWordList()) {
        if (word.getLinePresent().contains(i)) {
          line = line.concat("," + word.getValue());
        }
      }
      lines.add(line);
    }

    dataset1.setLines(lines);

    return dataset1;
  }

  // Get all line on file
  //-------------------------
  private List<String> getLines(File file) throws IOException {
    List<String> lines = new ArrayList<>();
    String line;
    BufferedReader in = new BufferedReader(new FileReader(file));

    while ((line = in.readLine()) != null) {

      if (!line.isBlank()) {
        lines.add(line);
      }

    }
    in.close();

    return lines;
  }


  // Test if str is numeric
  //-----------------------------
  private boolean isNumeric(String str) {

    if (str == null || str.length() == 0) {
      return false;
    }

    try {
      Integer.parseInt(str);
      return true;

    } catch (NumberFormatException e) {
      return false;
    }
  }

  // Get column line number value
  //--------------------------------
  private int getLineNumber(String str) throws DatasetException {
    for (String s : str.split(",")) {

      if (isNumeric(s)) {
        return Integer.parseInt(s);
      }

    }
    throw new DatasetException("Echec de chargement du dataset");
  }

  // Get column line number value
  //--------------------------------
  private int convertToInt(String str) {
    if (isNumeric(str)) {
      return Integer.parseInt(str);
    }
    throw new DatasetException("Echec de conversion");
  }
}
