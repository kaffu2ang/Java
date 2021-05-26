import java.util.Arrays;
import java.util.Scanner;

public class LISP {
    public static void main(String[] args) throws Exception {
            ArrayStack OPTR = new ArrayStack();
            ArrayStack OPND = new ArrayStack();
            ListEvaluate le = new ListEvaluate();
            le.EvaluateExpression(OPTR, OPND);
            int sum = (int) OPND.GetTop();
            System.out.println("表达式的结果为：" + sum);

    }
}
class  ListEvaluate{
    private static String expression;//表达式
    private static int index=0;//表达式的第 index 个位置
    private static char c; //表达式第index个位置上的对应的字符
    public ListEvaluate(){
        Scanner in = new Scanner(System.in);
        System.out.println("请输入表达式：");
        expression = in.next();
        expression+="#"; //在表达式结尾加入#，以免输入的时候忘记输入
        c=expression.charAt(index);
    }
    public boolean isDight(char c){ //判断字符是否是数字
        if(c>='0'&&c<='9') return true;
        else return false;
    }
    public int Operate(char x,int a,int b){ //根据数字和符号计算并返回结果
        int sum=0;
        switch(x){
            case '+':{sum=a+b;break;}
            case '-':{sum=a-b;break;}
            case '*':{sum=a*b;break;}
            case '/':{sum=a/b;break;}
        }
        return sum;
    }
    public int EvaluateExpression(ArrayStack OPTR,ArrayStack OPND ) throws Exception {
    /*
    * 运用递归的思想，分别计算每个括号的内容
    * OPTR存放运算符，OPND存放数字(或者运算结果）
    * */
    OPTR.Push('#');
    int num=0;//存放从表达式中读取到的数字
    char x; //存放从表达式中读取到的运算符
    while (c!='#'||(char)OPTR.GetTop()!='#'){
        if (c=='('){
            //遇到左括号，开始递归
            c = expression.charAt(++index);//读取表达式的下一个字符
            int sum = EvaluateExpression(new ArrayStack(),new ArrayStack());//递归，返回括号里的运算结果
            OPND.Push(sum);//将运算结果压入数字栈
            if (OPND.StackLength()==2){
                //果然数字栈长度为2，说明可以进行运算了
                if ((char)OPTR.GetTop()=='#'){ //如果运算符栈里没有运算符，说明表达式错误
                    throw new Exception("表达式错误");
                }
                else{
                    x =(char)OPTR.Pop();
                    int b =(int) OPND.Pop();
                    int a = (int)OPND.Pop();
                    OPND.Push(Operate(x,a,b));//计算 a x b 的结果，并压入结果栈中

                }
            }

        }
        if (c==')'&&(char)OPTR.GetTop()=='#'){
            //如果读取到右括号，并且运算符栈为空（即栈顶是#号）说明该括号内容计算完毕
            c = expression.charAt(++index);//读取下一个字符
            return (int)OPND.GetTop();//返回运算结果（即栈顶元素）
        }
        if (isDight(c)==true){
            //如果该字符是数字
            num =(int)(c-'0');//char转int
            OPND.Push(num);
            if (OPND.StackLength()==2){
                //果然数字栈长度为2，说明可以进行运算了
                if ((char)OPTR.GetTop()=='#'){
                    throw new Exception("表达式错误");
                }
                else{
                    x =(char)OPTR.Pop();
                    int b =(int) OPND.Pop();
                    int a = (int)OPND.Pop();
                    OPND.Push(Operate(x,a,b));//计算 a x b 的结果，并压入结果栈中

                }
            }
            c=expression.charAt(++index);
        }else if (c!='#'&&c!='('&&c!=')'){
            //读取到的字符是运算符
            OPTR.Push(c);
            c=expression.charAt(++index);
        }
    }
    return (int)OPND.GetTop();
    }
}
class   ArrayStack<T> { //栈的相关方法

    private int max_size;//栈的容量
    private T[] array;//栈数组
    private int top;//栈顶指针

    public ArrayStack() { //申请内存空间
        this.max_size = 100;
        array = (T[]) new Object[max_size];
        this.top = -1;
    }

    public ArrayStack(int size) {
        this.max_size = size;
        array = (T[]) new Object[max_size];
        this.top = -1;
    }

    public void Push(T t) { //入栈
        top++;
        if (top > array.length - 1) {
            T[] copy = Arrays.copyOf(array, max_size * 2);
            array = copy;
        }
        array[top] = t;
    }

    public T Pop() throws Exception { //出栈
        if (top >= 0) {
            return array[top--];
        } else {
            throw new Exception("空栈，无法进行出栈操作");
        }
    }

    public T GetTop() throws Exception { //获得栈顶元素
        if (top < 0) throw new Exception("空栈，栈顶为空");
        else return array[top];
    }

    public boolean isEmpty() { //栈的判空
        if (top == -1) return true;
        else return false;
    }
    public int StackLength(){ //返回栈的长度
        return top+1;
    }
}
