package com.polyu.comp5134.ui.view;
import javax.swing.JPanel;
import java.util.Observer;
import java.util.Observable;
import com.polyu.comp5134.domain.model.*;

public abstract class ELView extends JPanel implements Observer{

	public abstract void update(Observable o, Object arg);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
