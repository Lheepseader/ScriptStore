package com.gameparty.ScriptStore.model;

import com.gameparty.ScriptStore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {
    private Long id;
    private String username;

    public static UserModel toModel(User user) {
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setUsername(user.getUsername());
        return model;
    }


}
