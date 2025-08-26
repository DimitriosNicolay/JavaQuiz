package service.dto;

import java.util.List;

public class QuestionDTO {
    private int id;
    private int topicId;        // maps to topic_id in DB
    private String title;       // maps to title column (question title)
    private String description; // maps to description column (question text)

    // List of associated answers
    private List<AnswerDTO> answers;

    public QuestionDTO() {}

    public QuestionDTO(int id, int topicId, String title, String description) {
        this.id = id;
        this.topicId = topicId;
        this.title = title;
        this.description = description;
    }

    // Constructor including answers
    public QuestionDTO(int id, int topicId, String title, String description, List<AnswerDTO> answers) {
        this.id = id;
        this.topicId = topicId;
        this.title = title;
        this.description = description;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // answers
    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
