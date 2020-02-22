import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class MainWindowMineSweeper extends JFrame {
    public static void main(String[] args) {
        MainWindowMineSweeper window = new MainWindowMineSweeper();
        window.setVisible(true);
    }
    
    private static JButton newGame;
    private static JLabel warning;
    private static JLabel gamesWon;
    private static JLabel gamesLost;
    private static JLabel gameInProcess;
    private static MainWindowMineSweeper instance;
    private static int counterW = 1;
    private static int counterL = 1;
    
    public MainWindowMineSweeper() {
        instance = this;
        setSize(300, 200);
        setTitle("Mine Sweeper Menu!");
        gamesWon = new JLabel("Games Won: 0");
        gamesLost = new JLabel("Games Lost: 0");
        warning = new JLabel("***Please lower the volume on your device***");
        gameInProcess = new JLabel("Welcome to Mine Sweeper!");
        newGame = new JButton("New Game");
        
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineSweeperGame game = new MineSweeperGame();
                game.startGame();
                newGame.setEnabled(false);
            } 
        });
        
        Container pane = getContentPane();
        pane.add(warning, BorderLayout.PAGE_START);
        pane.add(gamesWon, BorderLayout.LINE_START);
        pane.add(newGame, BorderLayout.CENTER);
        pane.add(gamesLost, BorderLayout.LINE_END);
        pane.add(gameInProcess, BorderLayout.PAGE_END);
    }
    
    public static void gamesWonLabel() {
        gamesWon.setText("Games Won: "+counterW);
        counterW++;
        newGame.setEnabled(true);
        gameInProcess.setText("The game was won! Start a new game?");
    }
    
    public static void gamesLostLabel() {
        gamesLost.setText("Games Lost: "+counterL/10);
        counterL++;
        newGame.setEnabled(true);
        gameInProcess.setText("The game was lost! Start a new game?");
    }   
}