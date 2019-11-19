package lamda;

@FunctionalInterface
//public interface NextItemProcessor<Stream> {//固定和非固定
//   Stream apply();
//}
public interface NextItemProcessor<T> {//固定和非固定
   Stream<T> apply();
}
