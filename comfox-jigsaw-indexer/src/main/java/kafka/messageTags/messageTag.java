package kafka.messageTags;

public interface MessageTag {
    void execute(String tag, String jsonData);
}
