package lamda;

@FunctionalInterface
//public interface NextItemProcessor<Stream> {//固定和非固定
//   Stream apply();
//}
public interface NextItemProcessor<T> {//固定和非固定
   SerialStream<T> apply();//特定的SerialStream<T>和返回T的Supplier不一样
}
