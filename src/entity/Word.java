package entity;

import java.util.ArrayList;
import java.util.List;

public class Word {
  private String value;
  private List<Integer> linePresent;

  public Word() {
    this.linePresent = new ArrayList<>();
  }

  public Word(String value) {
    this.value = value;
    this.linePresent = new ArrayList<>();
  }

  public Word(String value, List<Integer> linePresent) {
    this.value = value;
    this.linePresent = linePresent;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public List<Integer> getLinePresent() {
    return linePresent;
  }

  public void setLinePresent(List<Integer> linePresent) {
    this.linePresent = linePresent;
  }

  public void addLine(Integer line) {
    this.linePresent.add(line);
  }
}
