package calculator;

/**
 * The abstract Calculator class which implements the Calculator interface.
 * It contains the final fields for a single integer calculation:
 * two integers and an operator (mode).
 * One AbstractCalculator Object only has 1 possible result.
 * It inherits the two public functions from the user interface: input() and getResult().
 * It contains two basic functions which does two basic number operations.
 */
public abstract class AbstractCalculator implements Calculator {
  // The result to be shown
  protected final String result;
  // A temporary placeholder for operands waiting to be operated
  protected final Integer tmp;
  // Operation modes: +, -, or *
  protected final Character mode;
  // A number to be operated
  protected final Integer operand;

  /**
   * The default constructor.
   *
   * @param result  The result to be shown by getResult().
   * @param tmp  A temporary placeholder for operands waiting to be operated.
   * @param mode  Operation modes: +, -, or *.
   * @param operand  A number to be operated.
   */
  protected AbstractCalculator(String result, Integer tmp, Character mode, Integer operand) {
    this.result = result;
    this.tmp = tmp;
    this.mode = mode;
    this.operand = operand;
  }

  /**
   * It passes the inputs to the controller.
   * The controller will check the inputs' validity and manage calculations.
   * Moreover, the controller is a factory method.
   * It will decide which Calculator implementation it returns.
   *
   * @param in a single char as the input.
   * @return A class which implements Calculator interface.
   */
  @Override
  public Calculator input(char in) {
    return controller(in);
  }

  /**
   * Send the result to the user. The result is obtained before this class is instantiated.
   *
   * @return the calculation result of this Calculator class. "=" is ignored.
   */
  @Override
  public String getResult() {
    return result.replace("=", "");
  }

  /**
   * A model which does the operation in this object.
   * when only one operand exists (can't operate), return this operand
   * Else, does + - * operations on the two operands
   *
   * @return the result of the operation or 0 when overflow or underflow.
   * @throws IllegalArgumentException when operand doesn't exist.
   */
  protected int operate() throws IllegalArgumentException {
    if (operand == null) {
      throw new IllegalArgumentException("No operand");
    }
    if (mode == null) {
      return operand;
    }

    long result;
    switch (mode) {
      case '+':
        result = tmp.longValue() + operand;
        break;
      case '-':
        result = tmp.longValue() - operand;
        break;
      case '*':
        result = tmp.longValue() * operand;
        break;
      default:
        throw new IllegalArgumentException("Invalid operation mode");
    }
    if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
      return 0;
    } else {
      return (int) result;
    }
  }

  /**
   * Another model which rearranges inputs into numbers.
   * It is called only when inputs are digits.
   * e.g. 11 -> 1 * 10 + 1
   *
   * @param in inputs as digits.
   * @return the integer represented by the digit.
   * @throws IllegalArgumentException when overflows.
   */
  protected int addDigit(int in) throws IllegalArgumentException {
    if (operand == null) {
      return in;
    }
    long result = operand * 10L + in;
    if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
      throw new IllegalArgumentException("Input Overflows");
    }
    return operand * 10 + in;
  }

  /**
   * The controller which connects input, model(operate), and view(result).
   * It calls the models to calculate the result and passes the result to a new Calculator.
   *
   * @param in the input char.
   * @return a class implements Calculator decided by the implementation of this method.
   * @throws IllegalArgumentException when inputs are invalid or may cause error in operations.
   */
  protected abstract Calculator controller(char in);
}
