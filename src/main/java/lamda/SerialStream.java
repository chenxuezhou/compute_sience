package lamda;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * @param <T>
 */
public class SerialStream<T> implements Stream<T>{

    private T data;

    private boolean isEnd;
    //
    private NextItemProcessor nextItemProcessor;

    //List转stream流

    /**
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Stream<T>  ofStream(List<T> list){
        return ofStream(list.iterator(),true);
    }


    public static <T> Stream<T>  ofStream(Iterator<T> iterator,boolean isStart )   {
        //不仅二叉树可以用递归，链表也可以，  不需要通过stream传递深入构建，但有深度变量
        SerialStream<T> serialStream = new SerialStream<>();
        if (iterator.hasNext()){
            serialStream.isEnd=true;
            return serialStream;
        }
        if (isStart){
            serialStream.nextItemProcessor=()->ofStream(iterator,false);//构建头结点就不用深入传递
            return serialStream;
        }else {
            serialStream.data=iterator.next();
            serialStream.nextItemProcessor=()->ofStream(iterator,false);
            return serialStream;
        }
    }

//    public static <T> Stream<T>  map(SerialStream<T> stream,Function function)   {
//
//    }
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


}
