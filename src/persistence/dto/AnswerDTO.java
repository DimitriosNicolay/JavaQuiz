package persistence.dto;

public class AnswerDTO {
    private int id;
    private int questionId;
    private String title;
    private String description;
    private boolean isCorrect;

    public AnswerDTO() {}

    public AnswerDTO(int id, int questionId, String title, String description, boolean isCorrect) {
        this.id = id;
        this.questionId = questionId;
        this.title = title;
        this.description = description;
        this.isCorrect = isCorrect;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }
}
