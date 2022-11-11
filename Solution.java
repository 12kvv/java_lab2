import java.util.Stack;

/**
 * @author vilak
 *
 */
public class Solution {
	
	private static boolean check(String mathStr) {
		char[] c = mathStr.toCharArray();
		int cnt=0;
		boolean comp=false;
		for (int i = 0; i < c.length; i++) {//3*(-5*2)
			switch (c[i]) {
			case '(':
				cnt++;
				comp=false;
				break;
			case ')':
				cnt--;
				break;
			case '+':
			case '-':
			case '*':
			case '/':
				if(comp) {
					return false;
				}
				else comp=true;
				break;
			default:
				comp=false;
				break;
		}	
	}
		if(cnt!=0) return false;
		return true;
}
	
	private static String transfer(String mathStr) {
		// Пометить вывод
		StringBuilder result = new StringBuilder();
		// 1. Инициализируем стек операторов.
		Stack<Character> stack = new Stack();
		if (mathStr == null || mathStr.length() == 0) {
			return null;
		}else if(!check(mathStr))
			return null;
		System.out.println("--------------");
		System.out.println("Expression: " + mathStr);
		char[] arr = mathStr.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			// 2. Считывание по одному символу слева направо во входной строке из арифметического выражения.
			char s = arr[i];
			// 3. Если текущий символ является операндом, заполните суффиксное выражение напрямую.
			if (Character.isDigit(s)) {
				result.append(s);
			}
			// 4. Если текущим символом является (левая скобка, поместите его в стек операторов (определено на первом шаге).
			else if ('(' == s) {
				stack.push(s);
			}
			// 5. Если текущий символ является оператором, то
			else if ('+' == s || '-' == s || '*' == s || '/' == s) {
				if (!stack.isEmpty()) {
					char stackTop = stack.pop();
					// Когда приоритет этого оператора выше, чем у верхнего элемента стека, оператор помещается в стек оператора
					if (compare(s, stackTop)) {
						stack.push(stackTop);
						stack.push(s);
					}
					// В противном случае, поместите верхний оператор в стеке в суффиксное выражение и 
					//поместите текущий оператор в стек. Вернитесь к шагу 2.
					else {
						result.append(stackTop);
						stack.push(s);
					}
				}
				// 5.1. Когда стек операторов пуст, он помещается в стек операторов.
				else {
					stack.push(s);
				}
			}
			// 6. Если текущий символ является правой круглой скобкой, многократно вставляйте верхний элемент стека в суффиксное выражение до тех пор, пока верхний элемент стека не станет левой круглой скобкой (пока, и не извлеките левую круглую скобку из стека и удалите ее.
			else if (s == ')') {
				while (!stack.isEmpty()) {
					char item = stack.pop();
					if (item != '(') {
						result.append(item);
					} else {
						break;
					}
				}
			}
		}
		while (!stack.isEmpty()) {
			result.append(stack.pop());
		}
		//System.out.println("Суффиксное выражение:" + result.toString());
		return result.toString();
	}
  // приоритет сравнения
  private static boolean compare(char s, char item) {
  		if (item == '(') {
  			return true;
  		}
  		if (s == '*' || s == '/') {
  			if (item == '+' || item == '-') {
  				return true;
  			}
  		}
  		return false;
  	}
  
  private static Double calculate(String transferToPostfix) {
	  if(transferToPostfix==null) {
		  System.out.println("ERROR.");
		  return null;
	  }
		Stack<Double> stack = new Stack();
		char[] c = transferToPostfix.toCharArray();
		double a, b;
		for (int i = 0; i < c.length; i++) {
			if(c[i]=='+') {
				a = Double.valueOf(stack.pop().toString());
				b = Double.valueOf(stack.pop().toString());
				stack.push(b + a);
			}else if(c[i]=='-') {
				a = Double.valueOf(stack.pop().toString());
				b = Double.valueOf(stack.pop().toString());
				stack.push(b - a);
			}else if(c[i]=='*') {
				a = Double.valueOf(stack.pop().toString());
				b = Double.valueOf(stack.pop().toString());
				stack.push(b * a);
			}else if(c[i]=='/') {
				a = Double.valueOf(stack.pop().toString());
				b = Double.valueOf(stack.pop().toString());
				if (Math.abs (a)>=0.000000001) {
					stack.push(b / a);
				}
				else {
				System.out.println("ERROR.");
				stack.clear();
				return stack.push(null);
				}
				
			}
			else {
			Character d = c[i];
				stack.push(Double.valueOf(d.toString()));}
		}
		if (stack.size()!=1)
			System.out.println("ERROR.");
		return stack.pop();
	}
  public static void main(String[] args) { 
		System.out.println("Calculation result: " + calculate(transfer("1+2*+(3-4)")));
		System.out.println("Calculation result: " + calculate(transfer("2*3-9/0")));
		System.out.println("Calculation result: " + calculate(transfer("(5+2)/(8-1)")));
	}
}
