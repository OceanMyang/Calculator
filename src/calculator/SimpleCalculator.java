package calculator;

/**
 * A simple calculator allowing whole number calculations and + - * operations.
 * All fields in this class are final.
 * 1 SimpleCalculator Class only has 1 possible result.
 * Operations are done by the previous calculator before instantiation.
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

  /**
   * The controller which connects input, model(operate), and view(result).
   * It calls the models to calculate the result and passes the result to a new Calculator.
   * @param in inputs passed from input()
   * @return a new Calculator with a renewed state based on the input.
   * @throws IllegalArgumentException when inputs are invalid or models throw exceptions.
   */
  protected SimpleCalculator controller(char in) throws IllegalArgumentException {
    // input is a char from '0'-'9'
    if (48 <= in && in <= 57) {
      return new SimpleCalculator(result + in, tmp, mode, addDigit(in - 48));
    }
    switch (in) {
      // input is an operator.
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
