package calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for SimpleCalculator.
 */
public class SimpleCalculatorTest {
  private Calculator calculator;
  private String equation;
  private String[] expected;

  @Before
  public void setUp() {
    calculator = new SimpleCalculator();
  }

  @Test
  public void initialStatus() {
    assertEquals("", calculator.getResult());
  }

  @Test
  public void validSequence() {
    equation = "32+24-10=";
    expected = new String[]{"3", "32", "32+", "32+2", "32+24", "56-", "56-1", "56-10", "46"};
    for (int i = 0; i < equation.length(); i++) {
      calculator = calculator.input(equation.charAt(i));
      assertEquals(expected[i], calculator.getResult());
    }
  }

  @Test
  public void equalSignInMiddle() {
    equation = "32+24=-10=";
    expected = new String[]{"3", "32", "32+", "32+2", "32+24", "56", "56-", "56-1", "56-10", "46"};
    for (int i = 0; i < equation.length(); i++) {
      calculator = calculator.input(equation.charAt(i));
      assertEquals(expected[i], calculator.getResult());
    }
  }

  @Test
  public void immutable() {
    String result = calculator.getResult();
    calculator.input('1');
    assertEquals(result, calculator.getResult());
  }

  @Test
  public void invalidInputs() {
    for (char i = 0; i < 128; i++) {
      if (i < 48 || i > 57) {
        if (i != '+' && i != '-' && i != '*' && i != 'C') {
          char c = i;
          assertThrows(IllegalArgumentException.class, () -> calculator.input(c));
        }
      }
    }
  }

  @Test
  public void invalidFirstInputs() {
    for (char i : List.of('+', '-', '*')) {
      assertThrows(IllegalArgumentException.class, () -> calculator.input(i));
    }
  }

  @Test
  public void clearSequence() {
    equation = "1-1+2-3+3-3+2-1+1";
    calculator = calculator.input('C');
    assertEquals("", calculator.getResult());
  }

  @Test
  public void noInferAndNegativeInputs() {
    equation = "32+=";
    assertThrows(IllegalArgumentException.class, () -> execute(equation));
    equation = "+12+3";
    assertThrows(IllegalArgumentException.class, () -> execute(equation));
    equation = "-1";
    assertThrows(IllegalArgumentException.class, () -> execute(equation));
    equation = "1+-1";
    assertThrows(IllegalArgumentException.class, () -> execute(equation));
  }

  @Test
  public void negativeResultOk() {
    equation = "1-2+3-4=";
    assertEquals("-2", execute(equation));
  }

  @Test
  public void multipleEqualSigns() {
    assertEquals("32", execute("32="));
    assertEquals(execute("32+24=="), execute("32+24==="));
  }

  @Test(expected = IllegalArgumentException.class)
  public void inputOverFlow() {
    equation = "2147483648";
    assertEquals("2147483648", execute(equation));
  }

  @Test
  public void operationOverFlow() {
    equation = "1111111111*10=";
    assertEquals("0", execute(equation));
    equation = "2147483647+1-10=";
    assertEquals("-10", execute(equation));
  }

  @Test
  public void operationUnderFlow() {
    equation = "0-2-2147483647=";
    assertEquals("0", execute(equation));
  }

  @Test
  public void zeroBeforeNumber() {
    equation = "00000001=";
    assertEquals("1", execute(equation));
  }

  private String execute(String equation) throws IllegalArgumentException {
    calculator = calculator.input('C');
    for (int i = 0; i < equation.length(); i++) {
      calculator = calculator.input(equation.charAt(i));
    }
    return calculator.getResult();
  }
}