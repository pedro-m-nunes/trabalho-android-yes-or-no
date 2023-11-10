package br.ifsul.quatroi.yesorno.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YesOrNo {

    @Expose
    private String answer;

    @Expose
    private boolean forced;

    @Expose
    private String image;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public YesOrNo(String answer, boolean forced, String image) {
        this.answer = answer;
        this.forced = forced;
        this.image = image;
    }

    public YesOrNo() {}

    @Override
    public String toString() {
        return "YesOrNo{" +
                "answer='" + answer + '\'' +
                ", forced=" + forced +
                ", image='" + image + '\'' +
                '}';
    }
}
