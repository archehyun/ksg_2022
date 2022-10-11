package com.ksg.api.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Menu {
    
    private int menu_id;
    private int menu_parent_id;
    private String menu_name;
    private String menu_icon;
    private String menu_url;
    private MenuType type;
    public static enum MenuType
    {
        TITLE, MENU, EMPTY
    }

    
}
