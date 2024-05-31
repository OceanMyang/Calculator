package calculator;

/**
 *
 */
public class SmartCalculator extends AbstractCalculator {

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
    super(result, tmp, mode, operand);
  }

  /**
   * The controller which connects input, model(operate), and view(result).
   * It calls the models to calculate the result and passes the result to a new Calculator.
   * @param in inputs passed from input()
   * @return a new Calculator with a renewed state based on the input.
   * @throws IllegalArgumentException when inputs are invalid or models throw exceptions.
   */
  @Override
  protected SmartCalculator controller(char in) throws IllegalArgumentException {
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
          if (mode != null) {
            return new SmartCalculator(
                    result + in,
                    tmp,
                    in,
                    null);
          } else if (in == '+') {
            return new SmartCalculator(
                    result + in, null, null, null);
          } else {
            throw new IllegalArgumentException("Illegal Start of expression");
          }
        }

        // e.g. 32+16+ -> 48+:
        // In this case, saves 48 as a result
        // saves 48 in temporary storage for later calculations and changes the calculation mode
        return new SmartCalculator(
                Integer.toString(operate()) + in,
                operate(),
                in,
                null);
      case '=':
        // Prevent user to only enter =
        if (operand == null) {
          if (mode != null) {
            int result = new SmartCalculator("", tmp, mode, tmp).operate();
            return new SmartCalculator(
                    Integer.toString(result), result, mode, tmp);
          }
          throw new IllegalArgumentException("No number before equal sign");
        }
        if (result.endsWith("=")) {
          int result = new SmartCalculator("", operate(), mode, operand).operate();
          return new SmartCalculator(
                  Integer.toString(result), result, mode, operand);
        } else {
          return new SmartCalculator(
                  Integer.toString(operate()) + '=', tmp, mode, operand);
        }
      // input is the clear command.
      case 'C':
        return new SmartCalculator();
      // other invalid inputs.
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
  }
}
