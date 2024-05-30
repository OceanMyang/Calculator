package calculator;

public class SmartCalculator implements Calculator {
  // The result to be shown
  private final String result;
  // A temporary placeholder for operands waiting to be operated
  private final Integer tmp;
  // Operation modes: +, -, or *
  private final Character mode;
  // A number to be operated
  private final Integer operand;

  /**
   * The only constructor accessible to the user.
   */
  public SmartCalculator() {
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
  private SmartCalculator(String result, Integer tmp, Character mode, Integer operand) {
    this.result = result;
    this.tmp = tmp;
    this.mode = mode;
    this.operand = operand;
  }

  // passes the inputs to the controller
  @Override
  public SmartCalculator input(char in) {
    return controller(in);
  }

  // returns the calculation result of this Calculator class.
  // the result is obtained before this class is instantiated.
  @Override
  public String getResult() {
    return result;
  }

  /**
   * A model which does the operation in this object.
   * when only one operand exists (can't operate), return this operand
   * Else, does + - * operations on the two operands
   *
   * @return the result of the operation or 0 when overflow or underflow.
   * @throws IllegalArgumentException when operand doesn't exist.
   */
  private int operate() throws IllegalArgumentException {
    if (operand == null) {
      throw new IllegalArgumentException("No operand");
    }
    if (tmp == null) {
      return operand;
    }
    Long l;
    switch (mode) {
      case '+':
        l = tmp.longValue() + operand;
        break;
      case '-':
        l = tmp.longValue() - operand;
        break;
      case '*':
        l = tmp.longValue() * operand;
        break;
      default:
        throw new IllegalArgumentException("Invalid operation mode");
    }
    if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
      return 0;
    } else {
      return l.intValue();
    }
  }

  /**
   * Another model which rearranges inputs into numbers.
   * It is called only when inputs are digits.
   * e.g. 11 -> 1 * 10 + 1
   * @param in inputs as digits.
   * @return the integer represented by the digit.
   * @throws IllegalArgumentException when overflows.
   */
  private int addDigit(int in) throws IllegalArgumentException {
    if (operand == null) {
      return in;
    }
    long l = operand * 10L + in;
    if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
      throw new IllegalArgumentException("Input Overflows");
    }
    return operand * 10 + in;
  }

  /**
   * The controller which connects input, model(operate), and view(result).
   * It calls the models to calculate the result and passes the result to a new Calculator.
   * @param in inputs passed from input()
   * @return a new Calculator with a renewed state based on the input.
   * @throws IllegalArgumentException when inputs are invalid or models throw exceptions.
   */
  private SmartCalculator controller(char in) throws IllegalArgumentException {
    // input is a char from '0'-'9'
    if (48 <= in && in <= 57) {
      return new SmartCalculator(result + in, tmp, mode, addDigit(in - 48));
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
        return new SmartCalculator(Integer.toString(operate()) + in, operate(), in, null);
      case '=':
        // Prevent user to only enter =
        if (operand == null) {
          throw new IllegalArgumentException("No number before equal sign");
        }
        return new SmartCalculator(Integer.toString(operate()), null, null, operate());
      // input is the clear command.
      case 'C':
        return new SmartCalculator();
      // other invalid inputs.
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
  }
}