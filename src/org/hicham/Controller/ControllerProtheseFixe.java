package org.hicham.Controller;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.hicham.Main.Main;
import org.hicham.Model.ActQueries;
import org.hicham.Model.ImageProtheseFixe;
import org.hicham.Model.PatientQueries;
import org.hicham.Model.ProtheseFixe;
import org.hicham.Model.ProtheseFixeQueries;
import org.hicham.View.ActPatientView;
import org.hicham.View.InfoPatient;
import org.hicham.View.OdfPatient;
import org.hicham.View.Ordonance;
import org.hicham.View.ProtheseFixeView;
import org.hicham.View.ProthesePartielleView;
import org.hicham.View.ProtheseTotaleView;

public class ControllerProtheseFixe {
	ProtheseFixeView protheseFixeView= new ProtheseFixeView();
	ProtheseFixeQueries protheseFixeQueries= new ProtheseFixeQueries();
	
	ProtheseTotaleView protheseTotaleView= new ProtheseTotaleView();
	ProthesePartielleView prothesePartielleView= new ProthesePartielleView();
	ActPatientView actPatientView= new ActPatientView();
	ActQueries actQueries        = new     ActQueries();
	PatientQueries patientQueries= new PatientQueries();
	OdfPatient odfPatient= new OdfPatient();
	Ordonance ordonance= new Ordonance();
	InfoPatient infoPatient = new InfoPatient();


	ControllerInfoPatient controllerInfoPatient= new ControllerInfoPatient(infoPatient
			,patientQueries
			,actPatientView,odfPatient,protheseFixeView,prothesePartielleView,protheseTotaleView,ordonance);


	int returnVal;
	JFileChooser filechooser= new JFileChooser();
	
	List<String>imagePaths= new ArrayList<>();
	ProtheseFixe currentProtheseFixe= new ProtheseFixe();
	
	List<String> imageOrder= new ArrayList<>();
	List<String> deletedImages= new ArrayList<>();
	List<String> addedImages= new ArrayList<>();
	int selectedImage;
	
	double montantActuel=0;
    
	public ControllerProtheseFixe(ProtheseFixeView protheseFixeView
			,ProtheseFixeQueries protheseFixeQueries,InfoPatient infoPatient
			,ControllerInfoPatient controllerInfoPatient){
		this.protheseFixeQueries= protheseFixeQueries;
		this.protheseFixeView= protheseFixeView;
		this.infoPatient= infoPatient;
		this.controllerInfoPatient= controllerInfoPatient;
		this.protheseFixeView.addProtheseFixActionListener(new ProtheseFixActionListener() );
	}

	class ProtheseFixActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource()==protheseFixeView.getAjoute()) {
				if(!controllerInfoPatient.patientSelected){
					int input = JOptionPane.showOptionDialog(null
							,"Vous n'avez pas selectioné un patient. "
							, "Erreur Patient"
							, JOptionPane.OK_CANCEL_OPTION
							, JOptionPane.INFORMATION_MESSAGE, null, null, null);

					if(input == JOptionPane.OK_OPTION){

					}	
				}else{
					//Check if image panel doesn't contain images 
					int countComponent=0;
					for (java.awt.Component labeliterator:protheseFixeView.getImagePanel().getComponents()){
						countComponent++;
					}
					if (countComponent==0) {
						int input = JOptionPane.showOptionDialog(null
								,"Vous n'avez pas ajouté des images, continué sans ajouté? "
								, "Ajout d'image"
								, JOptionPane.OK_CANCEL_OPTION
								, JOptionPane.INFORMATION_MESSAGE, null, null, null);

						if(input == JOptionPane.OK_OPTION){
							ajouteProthese();
							//refresh combo code
							refreshComboProthese();
						}	

					}
					else{
						ajouteProthese();
						//refresh combo code
						refreshComboProthese();

					}
				}
			}
			if (e.getSource()==protheseFixeView.getModifie()) {

				int input = JOptionPane.showOptionDialog(null
						, "Enrigestré les modification?"						,"Modifie Prothese"

						, JOptionPane.OK_CANCEL_OPTION
						, JOptionPane.INFORMATION_MESSAGE, null, null, null);

				if(input == JOptionPane.OK_OPTION){
					modifyFieldProtheseFixe();
					protheseFixeQueries.addProtheseFixe(currentProtheseFixe);
					List<String> oldImageLien= new ArrayList<>();
					List<String> newImagesLien= new ArrayList<>();
					for (int i = 0; i < currentProtheseFixe.getImageProtheseFixe().size(); i++) {
						oldImageLien.add(currentProtheseFixe.getImageProtheseFixe().get(i).getLien());
						
					}
					for (int i = 0; i < addedImages.size(); i++) {
						try{
						java.net.URL jarLocation = Main.class.getProtectionDomain().getCodeSource().getLocation();
						URL path=new java.net.URL(jarLocation, "");
						
						String jarPath=protheseFixeQueries.CopyFileImage(path.getPath()+"ImageProtheseFixe"
								, addedImages.get(i));
						//String newPath=protheseFixeQueries.CopyFileImage("C:/Users/Hicham/ImagesProtheseFixe", addedImages.get(i));
						}catch(Exception ex){
							ex.printStackTrace();
						}

					}
					protheseFixeQueries.addNewImages(addedImages, oldImageLien,currentProtheseFixe);
                    for (int i = 0; i < deletedImages.size(); i++) {
                    	System.out.println(deletedImages.toString());
						protheseFixeQueries.deleteProtheseImages(deletedImages.get(i));
					}
                    clearImageList();
				}	
			}
			if (e.getSource()==protheseFixeView.getSupp()) {
				//delete query
				int input = JOptionPane.showOptionDialog(null
						, "Etes vous sure de vouloir supprimer la prothese?"						,"Supprime Prothese"

						, JOptionPane.OK_CANCEL_OPTION
						, JOptionPane.INFORMATION_MESSAGE, null, null, null);

				if(input == JOptionPane.OK_OPTION){
					

					protheseFixeQueries.deleteProtheseFixe(currentProtheseFixe);
					protheseFixeView.clearImages();
					protheseFixeView.setEmptyFields();
					clearImageList();
					//refresh combo code
					refreshComboProthese();
				}	
			}
			if (e.getSource()==protheseFixeView.getAjouteImage()) {
				//add a new image to image panel
				//insert image into protheseImage
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter
						("Image files", ImageIO.getReaderFileSuffixes());
				filechooser.addChoosableFileFilter(imageFilter);
				filechooser.setAcceptAllFileFilterUsed(false);	

				returnVal = filechooser.showOpenDialog(null);
				String lienImage="";
				if(returnVal == JFileChooser.APPROVE_OPTION){
					try{
						lienImage=filechooser.getSelectedFile().getPath();
						showNewImage(lienImage);
						imagePaths.add(lienImage);
						imageOrder.add(lienImage);
						addedImages.add(lienImage);
						
					}catch(Exception ex){
						ex.printStackTrace();
					}

				}

				//show image in panel
			}
			if (e.getSource()==protheseFixeView.getNouveau()) {
				currentProtheseFixe= new ProtheseFixe();
				montantActuel=0;
				protheseFixeView.getAjoute().setEnabled(true);
				protheseFixeView.clearImages();
				clearImageList();
				protheseFixeView.setEmptyFields();	
			}
			if (e.getSource()== protheseFixeView.getListRVCombo()) {
				//set current prothese
				protheseFixeView.getAjoute().setEnabled(false);
				int selectedDate= protheseFixeView.getListRVCombo().getSelectedIndex();
				setSelectedProtheseFixeInfo(selectedDate);
				protheseFixeView.getPayementActuelText().setText("");
				protheseFixeView.getPayementTotalText().setText(new Double(currentProtheseFixe.getPayementTotal()).toString());
				montantActuel= currentProtheseFixe.getPayementActuel();

				protheseFixeView.clearImages();
				clearImageList();
			}
			if (e.getSource()== protheseFixeView.getDeleteImage()) {
				int input = JOptionPane.showOptionDialog(null
						, "Etes vous sure de vouloir supprimer l'image?"	,"Supprime Prothese"

						, JOptionPane.OK_CANCEL_OPTION
						, JOptionPane.INFORMATION_MESSAGE, null, null, null);

				if(input == JOptionPane.OK_OPTION){
					int counter=0;
					System.out.println("selected image"+ selectedImage);

					for (java.awt.Component labeliterator:protheseFixeView.getImagePanel().getComponents()){
						if (counter==selectedImage) {
							System.out.println("counter "+counter+" selected image"+ selectedImage);
							((JLabel) labeliterator).setIcon( null );
							protheseFixeView.getImagePanel().remove(labeliterator);
							//remove from image order
							deletedImages.add(imageOrder.get(selectedImage));
							imageOrder.remove(selectedImage);
							protheseFixeView.getImagePanel().revalidate();
							protheseFixeView.getImagePanel().repaint();
						}
						counter++;

						}
                    
	            	protheseFixeView.getShowImage().setVisible(false);				
				}	
				
			}
            if (e.getSource()== protheseFixeView.getAnnuleImage()) {
            	
            	//removing image label from internal frame
            	for (java.awt.Component labelIterator:protheseFixeView.getPanelShowImage().getComponents()){
            		
            		if (labelIterator instanceof JLabel) {
            			((JLabel) labelIterator).setIcon( null ); 
					}
        			
				}
            	protheseFixeView.getShowImage().setVisible(false);				
			}
            if (e.getSource()==protheseFixeView.getAddPay()) {
            	protheseFixeView.getPayementFrame().setVisible(true);

            }
            if (e.getSource()==protheseFixeView.getOkPay()) {            	
            	//modify the payement and payement total if changed
            	
            	double payementAjout=new Double(protheseFixeView.getPayementActuelText().getText());
            	double payementTotal= new Double( protheseFixeView.getPayementTotalText().getText());
            	double payementRest=protheseFixeQueries.updatePayement(currentProtheseFixe,payementAjout,payementTotal);
            	//update the rest label and the total and actual labels
            	//protheseFixeView.getPayementActuel().setText(new Double(currentProtheseFixe.getPayementActuel()).toString());
            	protheseFixeView.getPayementActuel().setText(new Double(montantActuel+payementAjout).toString());
            	montantActuel=montantActuel+payementAjout;
            	protheseFixeView.getPayementTotal().setText(new Double(payementTotal).toString());
            	protheseFixeView.getPayementRest().setText(new Double(payementRest).toString());
            	
            	protheseFixeView.getPayementFrame().setVisible(false);
            }
            if (e.getSource()==protheseFixeView.getAnnulePay()) {

            	protheseFixeView.getPayementFrame().setVisible(false);

			}
		}

	}
	public class ProtheseFixeMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
            JLabel label = (JLabel)e.getSource();            
            //iterate over panel components to get the current label
            int countComponent=0;
            for (java.awt.Component labeliterator:protheseFixeView.getImagePanel().getComponents()){
            	if (label.equals(labeliterator)) {
					selectedImage=countComponent;
				}
            	countComponent++;
    		}
            String lienFile= imageOrder.get(selectedImage);
            File imageFile= new File(lienFile);
            BufferedImage bfImageLabel=null;
            try{
    			 bfImageLabel = ImageIO.read(imageFile);
    		}catch(Exception ex){
    			ex.printStackTrace();
    		}
    		Image newimg = bfImageLabel.getScaledInstance( protheseFixeView.getImageLabel().getWidth(), 
    				protheseFixeView.getImageLabel().getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;
    		bfImageLabel.flush();
    		bfImageLabel = null;
    		protheseFixeView.getImageLabel().setIcon(new ImageIcon(newimg));
            protheseFixeView.getPanelShowImage().revalidate();
            protheseFixeView.getShowImage().setVisible(true);                       
		}
	
		
	}
	public synchronized JLabel setImageInLabel(File imageFile){
		BufferedImage bfImage=null;
		try{
			bfImage = ImageIO.read(imageFile);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		Image newimg = bfImage.getScaledInstance( 300, 300,  java.awt.Image.SCALE_SMOOTH ) ;
		JLabel picLabel=new JLabel();
		picLabel.setIcon(new ImageIcon(newimg));
		
		bfImage.flush();
		bfImage = null;
		protheseFixeView.addProtheseFixeMouseListener(new ProtheseFixeMouseListener(), picLabel);
		
		return picLabel;
	}
	public void makingImageLabels(List<String> liens){
		for (int i = 0; i < liens.size(); i++) {
			//create new label 
			JLabel label= new JLabel("");
			//add image from liens to the label
			File imageFile= new File(liens.get(i));
			label=setImageInLabel(imageFile);
			label.setCursor(new Cursor(Cursor.HAND_CURSOR));

			//add the label dynamically into the panel
			this.protheseFixeView.getImagePanel().add(label);
		}
		this.protheseFixeView.getImagePanel().revalidate();

	}
	public void showNewImage(String lien){
		//create new label 

		JLabel label= new JLabel("");
		//add image from liens to the label
		File imageFile= new File(lien);
		label= setImageInLabel(imageFile);
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//add the label dynamically into the panel
		this.protheseFixeView.getImagePanel().add(label);
		this.protheseFixeView.getImagePanel().revalidate();
	}
	public void modifyFieldProtheseFixe(){

		currentProtheseFixe.setDate(protheseFixeView.getDatePicker().getDate());  
		String time = new SimpleDateFormat("HH:mm").format(protheseFixeView.getTimePicker().getValue());		
		currentProtheseFixe.setTemp(time);
		currentProtheseFixe.setEntante(protheseFixeView.getEntente().getText());
		currentProtheseFixe.setTypeProthese(protheseFixeView.getTypeProthese().getText());
		currentProtheseFixe.setNumero(protheseFixeView.getNumero().getText());
		
	}
	public void clearImageList(){
		imagePaths.clear();	
		imageOrder.clear();
		deletedImages.clear();
		addedImages.clear();
	}
	public void setSelectedProtheseFixeInfo(int selectedProtheseFixe){
		ProtheseFixe protheseFixe= controllerInfoPatient.getCurrentPatient()
				.getProtheseFixes().get(selectedProtheseFixe);
		currentProtheseFixe= protheseFixe;
		protheseFixeView.getEntente().setText(protheseFixe.getEntante());
		protheseFixeView.getNumero().setText(protheseFixe.getNumero());
		protheseFixeView.getTypeProthese().setText(protheseFixe.getTypeProthese());
		protheseFixeView.getPayementActuel().setText(new Double(protheseFixe.getPayementActuel()).toString());
		protheseFixeView.getPayementTotal().setText(new Double(protheseFixe.getPayementTotal()).toString());
		String payementReste=new Double(protheseFixe.getPayementTotal()- protheseFixe.getPayementActuel()).toString();
		protheseFixeView.getPayementRest().setText(payementReste);
		protheseFixeView.getPayementTotalText().setText(new Double(protheseFixe.getPayementTotal()).toString());
		String dateValue= protheseFixe.getDate().toString();
		Date date= new Date();
		try {
			 date = new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		protheseFixeView.getDatePicker().setDate(date);
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date dateObject= new Date();
		try {
		      dateObject = formatter.parse(protheseFixe.getTemp());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		protheseFixeView.getTimePicker().setValue(dateObject);

		for (ImageProtheseFixe image : protheseFixe.getImageProtheseFixe() ) {

			//put a different thread for every Image
			new SwingWorker() {
				@Override
				protected Object doInBackground() throws Exception {
					JLabel label= new JLabel("");
					//add image from liens to the label
					File imageFile= new File(image.getLien());
					label= setImageInLabel(imageFile);
					label.setCursor(new Cursor(Cursor.HAND_CURSOR));
					protheseFixeView.getImagePanel().add(label);
					addImageOrder(image.getLien());
					protheseFixeView.getImagePanel().revalidate();			
					return null;
				}
				
				
			}.execute();
		}
	}
	public void ajouteProthese(){
		//insert images in protheseImages table
		Date date= protheseFixeView.getDatePicker().getDate();
		String time = new SimpleDateFormat("HH:mm").format(protheseFixeView.getTimePicker().getValue());		
		String num= protheseFixeView.getNumero().getText();
		String typeProthese= protheseFixeView.getTypeProthese().getText();
		String entante= protheseFixeView.getEntente().getText();
		double payTotal= new Double(protheseFixeView.getPayementTotalText().getText());
		double payActuel= new Double(protheseFixeView.getPayementActuelText().getText());

		currentProtheseFixe= new ProtheseFixe(num, entante, typeProthese, time, date,payTotal,payActuel);
		currentProtheseFixe.setPatient(controllerInfoPatient.getCurrentPatient());
		protheseFixeQueries.addProtheseFixe(currentProtheseFixe);
		//iterate over list of paths
		for (int i = 0; i < imageOrder.size(); i++) {
			//copying
			//destination should change when moving to jar file execution
			try {
				java.net.URL jarLocation = Main.class.getProtectionDomain().getCodeSource().getLocation();
				URL path=new java.net.URL(jarLocation, ".");
				String newImagePath=protheseFixeQueries.CopyFileImage(path.getPath()+"ImageProtheseFixe"
						,imageOrder.get(i) );
				ImageProtheseFixe imageProtheseFixe= new ImageProtheseFixe(newImagePath);//needs to change after copying
				imageProtheseFixe.setProtheseFixe(currentProtheseFixe);
				protheseFixeQueries.addProtheseFixeImage(imageProtheseFixe);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//clear image panel and set fields empty
		//set images empty
		protheseFixeView.clearImages();
		clearImageList();
		protheseFixeView.setEmptyFields();
	}
	public  void addImageOrder(String lien){
		imageOrder.add(lien);
	}
	public void refreshComboProthese(){
		int selectedItem=infoPatient.getRechCombo().getSelectedIndex();
		infoPatient.getRechCombo().setSelectedIndex(selectedItem);
	}
	
}
