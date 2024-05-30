package calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for SimpleCalculator.
 */
public class SimpleCalculatorTest extends CalculatorTest {
  @Override
  public void setUp() {
    calculator = new SimpleCalculator();
  }

  @Test
  public void invalidFirstInputs() {
    for (char i : List.of('+', '-', '*')) {
      assertThrows(IllegalArgumentException.class, () -> calculator.input(i));
    }
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
  public void multipleEqualSigns() {
    assertEquals("32", execute("32="));
    assertEquals(execute("32+24=="), execute("32+24==="));
  }
}