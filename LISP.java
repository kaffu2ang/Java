import java.util.Arrays;
import java.util.Scanner;

public class LISP {
    public static void main(String[] args) throws Exception {
            ArrayStack OPTR = new ArrayStack();
            ArrayStack OPND = new ArrayStack();
            ListEvaluate le = new ListEvaluate();
            le.EvaluateExpression(OPTR, OPND);
            int sum = (int) OPND.GetTop();
            System.out.println("���ʽ�Ľ��Ϊ��" + sum);

    }
}
class  ListEvaluate{
    private static String expression;//���ʽ
    private static int index=0;//���ʽ�ĵ� index ��λ��
    private static char c; //���ʽ��index��λ���ϵĶ�Ӧ���ַ�
    public ListEvaluate(){
        Scanner in = new Scanner(System.in);
        System.out.println("��������ʽ��");
        expression = in.next();
        expression+="#"; //�ڱ��ʽ��β����#�����������ʱ����������
        c=expression.charAt(index);
    }
    public boolean isDight(char c){ //�ж��ַ��Ƿ�������
        if(c>='0'&&c<='9') return true;
        else return false;
    }
    public int Operate(char x,int a,int b){ //�������ֺͷ��ż��㲢���ؽ��
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
    * ���õݹ��˼�룬�ֱ����ÿ�����ŵ�����
    * OPTR����������OPND�������(������������
    * */
    OPTR.Push('#');
    int num=0;//��Ŵӱ��ʽ�ж�ȡ��������
    char x; //��Ŵӱ��ʽ�ж�ȡ���������
    while (c!='#'||(char)OPTR.GetTop()!='#'){
        if (c=='('){
            //���������ţ���ʼ�ݹ�
            c = expression.charAt(++index);//��ȡ���ʽ����һ���ַ�
            int sum = EvaluateExpression(new ArrayStack(),new ArrayStack());//�ݹ飬�����������������
            OPND.Push(sum);//��������ѹ������ջ
            if (OPND.StackLength()==2){
                //��Ȼ����ջ����Ϊ2��˵�����Խ���������
                if ((char)OPTR.GetTop()=='#'){ //��������ջ��û���������˵�����ʽ����
                    throw new Exception("���ʽ����");
                }
                else{
                    x =(char)OPTR.Pop();
                    int b =(int) OPND.Pop();
                    int a = (int)OPND.Pop();
                    OPND.Push(Operate(x,a,b));//���� a x b �Ľ������ѹ����ջ��

                }
            }

        }
        if (c==')'&&(char)OPTR.GetTop()=='#'){
            //�����ȡ�������ţ����������ջΪ�գ���ջ����#�ţ�˵�����������ݼ������
            c = expression.charAt(++index);//��ȡ��һ���ַ�
            return (int)OPND.GetTop();//��������������ջ��Ԫ�أ�
        }
        if (isDight(c)==true){
            //������ַ�������
            num =(int)(c-'0');//charתint
            OPND.Push(num);
            if (OPND.StackLength()==2){
                //��Ȼ����ջ����Ϊ2��˵�����Խ���������
                if ((char)OPTR.GetTop()=='#'){
                    throw new Exception("���ʽ����");
                }
                else{
                    x =(char)OPTR.Pop();
                    int b =(int) OPND.Pop();
                    int a = (int)OPND.Pop();
                    OPND.Push(Operate(x,a,b));//���� a x b �Ľ������ѹ����ջ��

                }
            }
            c=expression.charAt(++index);
        }else if (c!='#'&&c!='('&&c!=')'){
            //��ȡ�����ַ��������
            OPTR.Push(c);
            c=expression.charAt(++index);
        }
    }
    return (int)OPND.GetTop();
    }
}
class   ArrayStack<T> { //ջ����ط���

    private int max_size;//ջ������
    private T[] array;//ջ����
    private int top;//ջ��ָ��

    public ArrayStack() { //�����ڴ�ռ�
        this.max_size = 100;
        array = (T[]) new Object[max_size];
        this.top = -1;
    }

    public ArrayStack(int size) {
        this.max_size = size;
        array = (T[]) new Object[max_size];
        this.top = -1;
    }

    public void Push(T t) { //��ջ
        top++;
        if (top > array.length - 1) {
            T[] copy = Arrays.copyOf(array, max_size * 2);
            array = copy;
        }
        array[top] = t;
    }

    public T Pop() throws Exception { //��ջ
        if (top >= 0) {
            return array[top--];
        } else {
            throw new Exception("��ջ���޷����г�ջ����");
        }
    }

    public T GetTop() throws Exception { //���ջ��Ԫ��
        if (top < 0) throw new Exception("��ջ��ջ��Ϊ��");
        else return array[top];
    }

    public boolean isEmpty() { //ջ���п�
        if (top == -1) return true;
        else return false;
    }
    public int StackLength(){ //����ջ�ĳ���
        return top+1;
    }
}
