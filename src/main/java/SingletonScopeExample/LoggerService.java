package SingletonScopeExample;

public class LoggerService {
    public void log(String message){
        System.out.println("Logger service instance: " + this.hashCode() + " - Logging message: " + message);
    }
}
