import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
public class MineSweeperGame extends JFrame implements KeyListener{
    JButton[] tiles;
    int[] gameBoard = new int[64];
    private boolean DEBUG_MODE = false;
    private boolean ctrlPressed = false;
    private int counterB = 0;
    private int counterEX = 0;
    
    public void startGame() {
        JFrame frame = new JFrame("Mine Sweeper Game!");
        frame.setSize(600, 600);
        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(8, 8));
        tiles = new JButton[64];
        for (int i=0; i<64; ++i) {
            tiles[i] = new JButton();
        }
        
        for (int i=0; i<64; ++i) {
            gameBoard[i] = 0;  
        } 

        for(int n=0; n<10; n++) {
            int r = (int) Math.floor(Math.random()*64);
            if(gameBoard[r] <= -900)  
            {
                n--;
                }
            if(DEBUG_MODE) {
                tiles[r].setText("BOOM");
                gameBoard[r] += -1000;
            }
            else {
                gameBoard[r] += -1000;
            }
            
            if((gameBoard[r] <= -900)&&(gameBoard[r] >= -1100)) {
                if((r>=9 && r<=14)||(r>=17 && r<=22)||(r>=25 && r<=30)|| 
                (r>=33 && r<=38)||(r>=41 && r<=46)||(r>=49 && r<=54)) {
                    gameBoard[r-9] += 1; gameBoard[r-8] += 1; gameBoard[r-7] += 1;
                    gameBoard[r-1] += 1; gameBoard[r+1] += 1; gameBoard[r+7] += 1;
                    gameBoard[r+8] += 1; gameBoard[r+9] += 1;
                }
            
                if((r>=1 && r<=6)) {
                    gameBoard[r-1] += 1; gameBoard[r+1] += 1; gameBoard[r+7] += 1;
                    gameBoard[r+8] += 1; gameBoard[r+9] += 1;
                }
            
                if((r>=57 && r<=62)) {
                    gameBoard[r-1] += 1; gameBoard[r+1] += 1; gameBoard[r-7] += 1;
                    gameBoard[r-8] += 1; gameBoard[r-9] += 1;
                }
            
                if((r==8)||(r==16)||(r==24)||(r==32)||(r==40)||(r==48)) {
                    gameBoard[r+1] += 1; gameBoard[r+8] += 1; gameBoard[r+9] += 1;
                    gameBoard[r-7] += 1; gameBoard[r-8] += 1;
                }
           
                if((r==15)||(r==23)||(r==31)||(r==39)||(r==47)||(r==55)) {
                    gameBoard[r-1] += 1; gameBoard[r-8] += 1; gameBoard[r-9] += 1;
                    gameBoard[r+7] += 1; gameBoard[r+8] += 1;
                }
            
                if(r==0) {
                    gameBoard[r+1] += 1; gameBoard[r+8] += 1; gameBoard[r+9] += 1;
                }
            
                if(r==7) {
                    gameBoard[r-1] += 1; gameBoard[r+7] += 1; gameBoard[r+8] += 1;
                }
            
                if(r==56) {
                    gameBoard[r+1] += 1; gameBoard[r-7] += 1; gameBoard[r-8] += 1;
                }
            
                if(r==63) {
                    gameBoard[r-1] += 1; gameBoard[r-8] += 1; gameBoard[r-9] += 1;
                } 
            }
        }
        
        for(int k = 0; k<64; k++) {
            if((gameBoard[k] > 0)&&(DEBUG_MODE)) {
                tiles[k].setText(String.valueOf(gameBoard[k])); 
            }
        }

        for(int p = 0; p<64; p++) {
            final int d = p;
            tiles[p].addKeyListener(this);
            tiles[p].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(((ctrlPressed)&&(tiles[d].getText().equals("")))||((ctrlPressed)&&(tiles[d].getText().equals("BOOM"))))  {
                        tiles[d].setText("MINE!");
                        if((tiles[d].getText().equals("MINE!"))&&(gameBoard[d] <= -900)) {
                            counterB++;
                            if(counterB == 10) {
                                MainWindowMineSweeper.gamesWonLabel();
                                File victory = new File("Victory.wav");
                                PlaySound(victory);
                                new java.util.Timer().schedule(new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));                                        
                                        }
                                    }, 
                                    2000
                                );
                            }
                        } 
                    }
                    
                    else if((ctrlPressed)&&(tiles[d].getText().equals("MINE!"))) {
                        tiles[d].setText("");
                        counterB--;
                    }

                    else {
                        tiles[d].setEnabled(false);
                        if((gameBoard[d] > 0)&&(!tiles[d].isEnabled())) {
                            tiles[d].setText(String.valueOf(gameBoard[d]));
                        }
                        
                        if((gameBoard[d] < -900)) {
                            for(int ex = 0; ex < 1; ex++) {
                                counterEX++;
                                if(counterEX == 1) {
                                    File explosion = new File("Explosion.wav");
                                    PlaySound(explosion);
                                }
                            }
                            
                            tiles[d].setText("BOOM");
                            for(int y = 0; y<64; y++) {
                                tiles[y].doClick(0);
                                new java.util.Timer().schedule(new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));                                        
                                        }
                                    }, 
                                    2000
                                );
                            }
                            MainWindowMineSweeper.gamesLostLabel();
                        }
                        
                        else if(gameBoard[d]==0) {
                            if((d>=9 && d<=14)||(d>=17 && d<=22)||(d>=25 && d<=30)|| 
                            (d>=33 && d<=38)||(d>=41 && d<=46)||(d>=49 && d<=54)) {
                                tiles[d-9].doClick(0); tiles[d-8].doClick(0);
                                tiles[d-7].doClick(0); tiles[d-1].doClick(0);
                                tiles[d+1].doClick(0); tiles[d+7].doClick(0);
                                tiles[d+8].doClick(0); tiles[d+9].doClick(0);
                            }

                            if((d>=1 && d<=6)) {
                                tiles[d-1].doClick(0); tiles[d+1].doClick(0);
                                tiles[d+7].doClick(0); tiles[d+8].doClick(0);
                                tiles[d+9].doClick(0);
                            }
            
                            if((d>=57 && d<=62)) {
                                tiles[d-1].doClick(0); tiles[d+1].doClick(0);
                                tiles[d-7].doClick(0); tiles[d-8].doClick(0);
                                tiles[d-9].doClick(0);
                            }
            
                            if((d==8)||(d==16)||(d==24)||(d==32)||(d==40)||(d==48)) {
                                tiles[d-8].doClick(0); tiles[d-7].doClick(0);
                                tiles[d+1].doClick(0); tiles[d+8].doClick(0);
                                tiles[d+9].doClick(0);
                            }
           
                            if((d==15)||(d==23)||(d==31)||(d==39)||(d==47)||(d==55)) {
                                tiles[d-8].doClick(0); tiles[d-9].doClick(0);
                                tiles[d-1].doClick(0); tiles[d+8].doClick(0);
                                tiles[d+7].doClick(0);
                            }
            
                            if(d==0) {
                                tiles[d+1].doClick(0); tiles[d+9].doClick(0);
                                tiles[d+8].doClick(0);
                            }
            
                            if(d==7) {
                                tiles[d-1].doClick(0); tiles[d+8].doClick(0);
                                tiles[d+7].doClick(0);
                            }
            
                            if(d==56) {
                                tiles[d+1].doClick(0); tiles[d-8].doClick(0);
                                tiles[d-7].doClick(0);
                            }
            
                            if(d==63) {
                                tiles[d-1].doClick(0); tiles[d-8].doClick(0);
                                tiles[d-9].doClick(0);
                            }  
                        }
                    }
                } 
            });
            
            frame.add(tiles[d]);
        }
        
        frame.setVisible(true);
    }
    
    @Override
    public void keyTyped(KeyEvent ke) { 
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        ctrlPressed = ke.isControlDown();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        ctrlPressed = ke.isControlDown();
    }
    
    static void PlaySound(File Sound) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Sound));
            clip.start();
            Thread.sleep(0);
        } catch(Exception e)
        {
        }
    }
}