package com.anjia.tumblr.types;

/**
 * This class represents a Post of type "answer"
 * @author jc
 */
public class AnswerPost extends Post {

    private String asking_name, asking_url;
    private String question;
    private String answer;

    /**
     * Get the asking URL
     * @return String URL
     */
    public String getAskingUrl() {
        return asking_url;
    }

    /**
     * Get the asking name
     * @return String name
     */
    public String getAskingName() {
        return asking_name;
    }

    /**
     * Get the question
     * @return String question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get the answer
     * @return String answer;
     */
    public String getAnswer() {
        return answer;
    }

    @Override
    public PostType getType() {
        return PostType.ANSWER;
    }

    public String getAsking_name() {
		return asking_name;
	}

	public void setAsking_name(String asking_name) {
		this.asking_name = asking_name;
	}

	public String getAsking_url() {
		return asking_url;
	}

	public void setAsking_url(String asking_url) {
		this.asking_url = asking_url;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
     * AnswerPost can not be saved
     * @throws IllegalArgumentException
     */
    @Override
    public void save() {
        throw new IllegalArgumentException("Cannot save AnswerPost");
    }

}