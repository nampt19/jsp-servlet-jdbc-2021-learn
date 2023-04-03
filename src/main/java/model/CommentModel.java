package model;

import lombok.*;

@Data
public class CommentModel  extends AbstractModel {
    private String content;
    private Long userId;
    private Long newId;

}
