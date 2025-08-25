package persistence.dto;

public class QuestionDTO {
    private int id;
    private int topicId;
    private String title;
    private String description;

    public QuestionDTO() {}

    public QuestionDTO(int id, int topicId, String title, String description) {
        this.id = id;
        this.topicId = topicId;
        this.title = title;
        this.description = description;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTopicId() { return topicId; }
    public void setTopicId(int topicId) { this.topicId = topicId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

