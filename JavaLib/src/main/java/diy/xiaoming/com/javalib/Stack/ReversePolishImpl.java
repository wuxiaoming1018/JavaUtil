package diy.xiaoming.com.javalib.Stack;

import java.util.Stack;

/**
 * 普通四则运算比波兰表达式实现
 * Created by Administrator on 2017-11-07.
 */

public class ReversePolishImpl {

    private String CLOSE_MARK = "bye";
    private boolean hasLeft = false;
    private Stack<Character> charStack = new Stack<>();
    private Stack<Integer> intStack = new Stack<>();
    private Stack<Object> resultStack = new Stack<>();
    private boolean isComplete;//是否是一个完整的括号
    private static String OPERATOR = "+-*/%^()";

    public void init() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入运算表达式");
//        String input = scanner.nextLine();
//        while (input.length() != 0 && !CLOSE_MARK.equals(input)) {
//            System.out.println("波兰表达式:");
//            toPolishDate(input);
//            System.out.println("逆波兰表达式:");
//            roReversePolish(input);
//        }
//        toPolishDate("3+5*2-(8-3)*2/4");
//        toPolishDate("8-2*3+(8-2)*5/6");
        String resultData = beforeData("-3+(2-5)*6/3");
        toPolishDate(resultData);
    }

    /**
     * 解决出现负数情况
     * -出现在最前面或者出现在左括号后面，则表示该数字为负数，在-前面加0
     * -5-(-8-1)会处理成 0-5-(0-8-1)
     * @param before
     * @return
     */
    private String beforeData(String before) {
        StringBuffer sb = new StringBuffer(before);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (OPERATOR.indexOf(c) >= 0) {
                if (i == 0) {
                    sb.insert(0, '0');
                    i++;
                } else if (sb.charAt(i - 1) == '(') {
                    sb.insert(i, '0');
                    i++;
                }
            }
        }
        return sb.toString();
    }

    private void roReversePolish(String input) {

    }

    private void toPolishDate(String input) {
        int length = input.length();
        char c;
        hasLeft = input.charAt(0) == '(';
        boolean isAdd = true;
        for (int i = 0; i < length; i++) {
            c = input.charAt(i);
            if (Character.isDigit(c)) {
                intStack.push(Integer.valueOf(c));
                resultStack.push(c);
            } else {
                if (charStack.isEmpty()) {
                    charStack.push(c);
                } else {
                    if (!charStack.isEmpty()) {
                        while (isAdd && priorityCompare(charStack.peek(), c)) {
                            resultStack.push(charStack.pop());//pop()获取栈顶元素并删除
                            isAdd = !charStack.isEmpty();
                        }
                    }
                    if (isComplete) {
                        //出现一个完整的括号
                        while (isAdd && charStack.peek() != '(') {
                            resultStack.push(charStack.pop());
                        }
                        charStack.pop();//去掉符号栈里面的'('
                        isComplete = false;
                    } else {
                        charStack.push(c);
                    }
                    isAdd = !charStack.isEmpty();
                }
            }
        }
        System.out.println("charStack: " + charStack);
        while (!charStack.isEmpty()) {
            resultStack.push(charStack.pop());
        }
        System.out.println("resultStack: " + resultStack);
    }

    /**
     * 优先级别对比 （当a运算符优先级别不小于b运算符时，a运算符弹出，新的栈顶运算符会继续
     * 和b运算符优先级进行比较，当小于时候，b运算符入栈）
     *
     * @param a 栈顶运算符
     * @param b 需要对比的运算符
     * @return true :栈顶运算符弹出  false ：b入符号栈
     */
    private boolean priorityCompare(char a, char b) {
        if (hasLeft) {
            if (b != ')') {
                return false;
            } else {
                isComplete = true;
                hasLeft = false;
                return false;
            }
        } else {
            if (b == '(') {
                hasLeft = true;
                return false;
            }
            if (a == b) {
                return true;
            }
            if (a == '+' || a == '-') {
                if (b == '-' || b == '+') {
                    return true;
                } else {
                    return false;
                }
            }
            if (a == '*' || a == '/') {
                return true;
            }

            if (a == '(') {
                return false;
            }
        }
        return false;
    }

}
