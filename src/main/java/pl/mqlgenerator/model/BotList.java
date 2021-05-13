package pl.mqlgenerator.model;

import pl.mqlgenerator.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class BotList {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @ManyToOne()
        private User user;

        @NotBlank
        @Column(nullable = false, unique = true)
        private String botName;

        @NotBlank
        @Column(nullable = false)
        @Lob
        private String botInString;



        public BotList() {
        }

        public BotList(User user, String botName, String botInString) {
                this.user = user;
                this.botName = botName;
                this.botInString = botInString;

        }

        public BotList(Long id, User user, String botName, String botInString) {
                this.id = id;
                this.user = user;
                this.botName = botName;
                this.botInString = botInString;

        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        public String getBotName() {
                return botName;
        }

        public void setBotName(String username) {
                this.botName = username;
        }

        public String getBotInString() {
                return botInString;
        }

        public void setBotInString(String password) {
                this.botInString = password;
        }


}
