package calculator;

/**
 * A simple calculator for whole number calculations and + - * operations.
 * It doesn't infer missing inputs and irregular expressions like 32+= or 32+-24.
 */
public class SimpleCalculator extends AbstractCalculator implements Calculator {
  /**
   * The only constructor accessible to the user.
   */
  public SimpleCalculator() {
    this("", null, null, null);
  }

  /**
   * The default constructor.
   *
   * @param result  The result to be shown by getResult().
   * @param tmp  A temporary placeholder for operands waiting to be operated.
   * @param mode  Operation modes: +, -, or *.
   * @param operand  A number to be operated.
   */
  private SimpleCalculator(String result, Integer tmp, Character mode, Integer operand) {
    super(result, tmp, mode, operand);
  }

  @Override
  protected SimpleCalculator controller(char in) throws IllegalArgumentException {
    // input is a digit.
    if (48 <= in && in <= 57) {
      return new SimpleCalculator(result + in, tmp, mode, addDigit(in - 48));
    }
    // input is an operator.
    switch (in) {
      case '+':
      case '-':
      case '*':
        // Prevent situations like 32++ and +12
        if (operand == null) {
          throw new IllegalArgumentException("No operand before operator");
        }
        // e.g. 32+16+ -> 48+:
        // In this case, saves 48 as a result
        // saves 48 in temporary storage for later calculations and changes the calculation mode
        return new SimpleCalculator(Integer.toString(operate()) + in, operate(), in, null);
      case '=':
        // Prevent user to only enter =
        if (operand == null) {
          throw new IllegalArgumentException("No number before equal sign");
        }
        return new SimpleCalculator(Integer.toString(operate()), null, null, operate());
      // input is the clear command.
      case 'C':
        return new SimpleCalculator();
      // other invalid inputs.
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
  }
}
