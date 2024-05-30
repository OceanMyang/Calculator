package calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SmartCalculatorTest extends CalculatorTest {

  @Override
  public void setUp() {
    calculator = new SmartCalculator();
  }

  @Test
  public void multipleEqualSigns() {
    equation = "32+24=";
    assertEquals("56", execute(equation));
    equation = "32+24==";
    assertEquals("80", execute(equation));
    equation = "32+24===";
    assertEquals("104", execute(equation));
  }

  @Test
  public void skipSecondOperand() {
    equation = "32+=";
    assertEquals("64", execute(equation));
    equation = "32+==";
    assertEquals("96", execute(equation));
  }

  @Test
  public void consecutiveOperators() {
    equation = "32+-24=";
    assertEquals("8", execute(equation));
  }

  @Test
  public void beginWithAdd() {
    equation = "+32-24=";
    assertEquals("8", execute(equation));
  }
}
