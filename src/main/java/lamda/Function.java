package lamda;
@FunctionalInterface
public interface Function {
    <R,T> R apply(T t);
}
