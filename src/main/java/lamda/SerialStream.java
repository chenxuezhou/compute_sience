package lamda;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/**
 * @param <T>
 */
public class SerialStream<T> implements Stream<T> {

    private T data;

    private boolean isEnd;
    //返回实现类的下一个环节的实现流
    private NextItemProcessor nextItemProcessor;
//    private Supplier<SerialStream<T>> nextItemProcessor;
    //List转stream流

    /**
     * @param list
     * @param <T>
     * @return
     */
    public static <T> SerialStream<T> ofStream(List<T> list) {
        return ofStream(list.iterator(), true);
    }


    public static <T> SerialStream<T> ofStream(Iterator<T> iterator, boolean isStart) {
        //不仅二叉树可以用递归，链表也可以，  不需要通过stream传递深入构建，但有深度变量
        SerialStream<T> serialStream = new SerialStream<>();
        if (iterator.hasNext()) {
            serialStream.isEnd = true;
            return serialStream;
        }
        if (isStart) {
            serialStream.nextItemProcessor = () -> ofStream(iterator, false);//构建头结点就不用深入传递
            return serialStream;
        } else {
            serialStream.data = iterator.next();
            serialStream.nextItemProcessor = () -> ofStream(iterator, false);
            return serialStream;
        }
    }
    //public <R, T> Stream<R> map(Function<R, T> function) 这里会使入参lamda表达式有问题，这里T应该是和类保持一致
    public <R> Stream<R> map(Function<R, T> function){//R和T反了
        //取得头结点Stream
        NextItemProcessor<T> headNode = this.nextItemProcessor;
        this.nextItemProcessor=()->{
            SerialStream<T> apply = headNode.apply();
            return map(apply,function);//直接返回实现类
        };
        SerialStream<R> newStream=new SerialStream<R>();
        newStream.nextItemProcessor=this.nextItemProcessor;
        return  newStream;
    }

    public static <R, T> SerialStream<R> map(SerialStream<T> stream, Function<R, T> function) {
        if (stream.isEnd){
            return  new SerialStream<>();
        }
        SerialStream<R> newStream=new SerialStream<R>();
        SerialStream<T> apply = stream.nextItemProcessor.apply();
        R r = function.apply(apply.data);
        newStream.data=r;
        newStream.nextItemProcessor = () -> {
            return map(apply,function);
        };
        return newStream;
//        SerialStream<R> serialStream = new SerialStream<>();
//        if (isStart) {
//            return map(serialStream, function, false);
//        } else {
//            R r = function.apply(stream.nextItemProcessor.apply());
//            serialStream.data = r;
//            return map(serialStream, function, false);
//        }
    }

    public T getData() {
        return data;
    }

    public SerialStream setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public SerialStream setEnd(boolean end) {
        isEnd = end;
        return this;
    }

    public static void main(String[] args) {
        SerialStream<Integer> integerStream = SerialStream.ofStream(Arrays.asList(1, 2, 3));
        Function<Integer,Integer> function=item->{return  item%2==0?item:0; };
//        integerStream.map(function);
        integerStream.map(item->{return  item%2==0?item:0; });
    }


}
