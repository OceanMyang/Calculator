package calculator;

public class SimpleCalculator implements Calculator {
  // The result to be shown
  private final String result;
  // A temporary placeholder for operands waiting to be operated
  private final Integer tmp;
  // Operation modes: +, -, or *
  private final Character mode;
  private final Integer operand;

  public SimpleCalculator() {
    this("", null, null, null);
  }

  private SimpleCalculator(String result, Integer tmp, Character mode, Integer operand) {
    this.result = result;
    this.tmp = tmp;
    this.mode = mode;
    this.operand = operand;
  }

  @Override
  public SimpleCalculator input(char in) {
    return controller(in);
  }

  @Override
  public String getResult() {
    return result;
  }

  private int operate() {
    if (operand == null) {
      throw new IllegalArgumentException("No operand");
    }
    if (tmp == null) {
      return operand;
    }
    switch (mode) {
      case '+':
        return tmp + operand;
      case '-':
        return tmp - operand;
      case '*':
        return tmp * operand;
      default:
        throw new IllegalArgumentException("Invalid operation mode");
    }
  }
  private int addDigit(int in) throws IllegalArgumentException {
    if (operand == null) {
      return in;
    }
    return operand * 10 + in;
  }

  private SimpleCalculator controller(char in) {
    // input is a char from '0'-'9'
    if (48 <= in && in <= 57) {
      if (operand == null) {
        return new SimpleCalculator(result + in, tmp, mode, addDigit(in - 48));
      }
      return new SimpleCalculator(result + in, tmp, mode, addDigit(in - 48));
    }
    switch (in) {
      case '+':
      case '-':
      case '*':
        // Prevent situations like 32++ and +12
        if (operand == null) {
          throw new IllegalArgumentException("No operand before operator");
        }
        // 32+16+ -> 48+: saves 48 in temporary storage for later calculations and as a result
        // and changes the calculation mode
        return new SimpleCalculator(Integer.toString(operate()) + in, operate(), in, null);
        // Prevent user to only enter =
        case '=':
        if (operand == null) {
          throw new IllegalArgumentException("No number before equal sign");
        }
        return new SimpleCalculator(Integer.toString(operate()), null, null, operate());
      case 'C':
        return new SimpleCalculator();
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
  }
}
