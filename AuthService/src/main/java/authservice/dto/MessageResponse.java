package authservice.dto;

import lombok.Data;

@Data
public class MessageResponse {
    private Object data;
    private String message;
    private Boolean status;

    public MessageResponse(String message , Boolean status , Object data ){
        this.message=message;
        this.status=status;
        this.data=data;
    }

    public MessageResponse(String message , Boolean status ){
        this.message=message;
        this.status=status;
    }

    public MessageResponse(String message ){
        this.message=message;
    }

}
