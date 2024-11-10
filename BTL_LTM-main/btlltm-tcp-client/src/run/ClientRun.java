package run;

import controller.SocketHandler;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import view.GameView;
import view.HistoryView;
import view.HomeView;
import view.InfoPlayerView;
import view.LoginView;
import view.MessageView;
import view.RegisterView;
import view.LeaderboardView;
public class ClientRun {
    public enum SceneName {
        LOGIN,
        REGISTER,
        HOMEVIEW,
        INFOPLAYER,
        MESSAGEVIEW,
        GAMEVIEW,
        LEADERBOARDVIEW,
        HISTORYVIEW
    }

    // scenes
    public static LoginView loginView;
    public static RegisterView registerView;
    public static HomeView homeView;
    public static GameView gameView;
    public static InfoPlayerView infoPlayerView;                        
    public static MessageView messageView;
    public static LeaderboardView leaderboardView;
    public static HistoryView historyView;

    // controller 
    // change
    // public static SocketHandler socketHandler;
    private SocketHandler socketHandler;

    public ClientRun() {
        socketHandler = new SocketHandler();
        initScene();
        connect();
    }
    
    public void initScene() {
        loginView = new LoginView();
        registerView = new RegisterView();
        homeView = new HomeView();
        infoPlayerView = new InfoPlayerView();
        messageView = new MessageView();
        gameView = new GameView();
        leaderboardView = new LeaderboardView();
        historyView = new HistoryView();
    }
    
    private void connect() {
        String ip = "192.168.1.197";
        int port = 8282;
        // connect to server
        new Thread(() -> {
            // call controller
            String result = ClientRun.getSocketHandler().connect(ip, port);

            // check result
            if (result.equals("success")) {
                onSuccess();
            } else {
                String failedMsg = result.split(";")[1];
                onFailed(failedMsg);
            }
        }).start();
    }
    
    private void onSuccess() {
        openScene(SceneName.LOGIN);

        System.out.println("connect to server thanh cong");
    }

    private void onFailed(String failedMsg) {
        JOptionPane.showMessageDialog(null, failedMsg, "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void openScene(SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case LOGIN:
                    loginView = new LoginView();
                    loginView.setVisible(true);
                    break;
                case REGISTER:
                    registerView = new RegisterView();
                    registerView.setVisible(true);
                    break;
                case HOMEVIEW:
                    homeView = new HomeView();
                    homeView.setVisible(true);
                    break;
                case INFOPLAYER:
                    infoPlayerView = new InfoPlayerView();
                    infoPlayerView.setVisible(true);
                    break;
                case MESSAGEVIEW:
                    messageView = new MessageView();
                    messageView.setVisible(true);
                    break;
                case GAMEVIEW:
                    gameView = new GameView();
                    gameView.setVisible(true);
                    break;
                case LEADERBOARDVIEW:
                    leaderboardView = new LeaderboardView();
                    leaderboardView.setVisible(true);
                    break;
                case HISTORYVIEW:
                    historyView = new HistoryView();
                    historyView.setVisible(true);
                    break;
                default:
                    break;
            }
        }
    }

    public static void closeScene(SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case LOGIN:
                    loginView.dispose();
                    break;
                case REGISTER:
                    registerView.dispose();
                    break;
                case HOMEVIEW:
                    homeView.dispose();
                    break;
                case INFOPLAYER:
                    infoPlayerView.dispose();
                    break;
                case MESSAGEVIEW:
                    messageView.dispose();
                    break;
                case GAMEVIEW:
                    gameView.dispose();
                    break;
                case LEADERBOARDVIEW:
                    leaderboardView.dispose();
                    break;
                case HISTORYVIEW:
                    historyView.dispose();
                    break;
                default:
                    break;
            }
        }
    }

    public static void closeAllScene() {
        loginView.dispose();
        registerView.dispose();
        homeView.dispose();
        infoPlayerView.dispose();
        messageView.dispose();
        gameView.dispose();
        leaderboardView.dispose();
        historyView.dispose();
    }

    public static SocketHandler getSocketHandler() {
        return getInstance().socketHandler;
    }

    private static ClientRun instance;

    public static ClientRun getInstance() {
        if (instance == null) {
            instance = new ClientRun();
        }
        return instance;
    }
    private static void setNimbusLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        setNimbusLookAndFeel();
        getInstance();
    }
}
