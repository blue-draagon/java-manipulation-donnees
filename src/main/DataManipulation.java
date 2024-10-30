package main;

import entity.Dataset;
import module.DatasetManipulation;

import java.io.File;

public class DataManipulation {
  public static void main(String[] args) {
    DatasetManipulation dm = new DatasetManipulation();

    // Load dataset 2
    Dataset dataset2 = dm.looadDataset(new File("src/Dataset2.txt"), DatasetManipulation.BOOLEEN_DATESET);

    // Convert to dataset 1
    Dataset dataset1 = dm.Dataset2ToDataset1(dataset2);

    // Write dataset 1
    dm.writeDataset(dataset1, new File("src/Dataset2ToDataset1.txt"));
  }
}
