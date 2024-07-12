module com.example.chatprojectfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chatprojectfx to javafx.fxml;
    exports com.example.chatprojectfx.Server;
    exports com.example.chatprojectfx.User;
    opens com.example.chatprojectfx.User to javafx.fxml;
}