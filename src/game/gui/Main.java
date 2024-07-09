package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private Stage stage;
	private Scene startMenu;
	private Scene Mode;
	private Button startButton;
	private Button instructionsButton;
	private Button hButton = new Button();
	private Button eButton = new Button();
	private Battle battle;
	private AnchorPane[] lanesAnchor;
	private AnchorPane[] wallsAnchor;
	private Button piercingCannon;
	private Button sniperCannon;
	private Button volleySpreadCannon;
	private Button wallTrap;
	private Button passButton;
	private Button automate;
	private Button buyLane1;
	private Button buyLane2;
	private Button buyLane3;
	private Button buyLane4;
	private Button buyLane5;
	private Button[] laneButtons;
	private Label scoreLabel;
	private Label phaseLabel;
	private Label resourceLabel;
	private Label turnLabel;
	private int heightLanes;
	private int widthWall;
	private VBox laneButtonMenu;
	private Popup insufficient;
	private boolean flag = false;
	private MediaPlayer mediaPlayerHome;
	private MediaPlayer mediaPlayerBattle;
	
	
	@Override
	public void start(Stage st) throws Exception {
		String musicFilePath = getClass().getResource("/BGMusic.mp3").toExternalForm();
        Media media = new Media(musicFilePath);
        mediaPlayerHome = new MediaPlayer(media);
        mediaPlayerHome.setVolume(5.0);
        mediaPlayerHome.setAutoPlay(true);
        mediaPlayerHome.setOnEndOfMedia(() -> mediaPlayerHome.seek(javafx.util.Duration.ZERO));
		
		stage = st;
		stage.setResizable(false);
		AnchorPane root = new AnchorPane();
		
		ImageView startView = new ImageView();
		Image startImage = new Image(getClass().getResourceAsStream("/StartBackground.png"));
		Image logoImg = new Image(getClass().getResourceAsStream("/Attack-On-Titan-Logo.png"));
		startView.setImage(startImage);

		Label title = new Label("Attack On Titan");
		title.getStyleClass().add("game-title-label");
		title.setLayoutX(422);
		title.setLayoutY(300);
        
		root.setPrefHeight(1200);
        root.setPrefWidth(750);
        root.getChildren().addAll(startView);
        
        startButton = new Button();
        startButton.setLayoutX(522);
        startButton.setLayoutY(400);
        startButton.setText("Start");
        startButton.setPrefWidth(156);
        startButton.setPrefHeight(54);
        startButton.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        startButton.setTextFill(javafx.scene.paint.Color.WHITE);
        startButton.setWrapText(true);
        startButton.setStyle("-fx-font-size: 24");
        startButton.setEffect(new InnerShadow());
        startButton.getStyleClass().add("StartButton");
        startButton.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle(ActionEvent event) {
        		difficulty();
        	}
        });
        
        instructionsButton = new Button();
        instructionsButton.setLayoutX(490);
        instructionsButton.setLayoutY(480);
        instructionsButton.setText("Instructions");
        instructionsButton.setPrefWidth(220);
        instructionsButton.setPrefHeight(54);
        instructionsButton.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        instructionsButton.setTextFill(javafx.scene.paint.Color.WHITE);
        instructionsButton.setWrapText(true);
        instructionsButton.setStyle("-fx-font-size: 24");
        instructionsButton.setEffect(new InnerShadow());
        instructionsButton.getStyleClass().add("StartButton");
        instructionsButton.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle(ActionEvent event) {
        		instructions();
        	}
        });
        
        root.getChildren().addAll(startButton, instructionsButton, title);

		startMenu = new Scene(root,1200,750);
		startMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setTitle("Attack on Titan");
		stage.getIcons().add(logoImg);
		stage.setScene(startMenu);
		stage.show();
		
	}
	
	private Button editModeButton(Button b, String text) {
		b.setPrefWidth(156);
		b.setPrefHeight(54);
		b.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		b.setTextFill(javafx.scene.paint.Color.WHITE);
		b.setWrapText(true);
		b.setStyle("-fx-font-size: 24");
		b.setEffect(new InnerShadow());
		b.setText(text);
		b.setLayoutX(222);
		b.setLayoutY(520);
		return b;
	}

	private void instructions() {
		AnchorPane instructionsPane = new AnchorPane();
		ImageView instructionBack = new ImageView();
		Image instImg = new Image(getClass().getResourceAsStream("/Untitled.jpg"));
		instructionBack.setImage(instImg);
		Label instructionsLabel = new Label("Attack on Titan: Utopia is a one-player, endless, tower defense game inspired by the hit anime Attack on Titan. Player must deploy weapons to lanes to protect the walls from approaching titans.");
		instructionsLabel.getStyleClass().add("instructions");
		instructionsLabel.setMaxWidth(1200);
		instructionsLabel.setMaxHeight(85);
		HBox titansDescribe = new HBox(20);
		titansDescribe.getStyleClass().add("bak1");
		titansDescribe.setAlignment(Pos.CENTER);
		titansDescribe.setMinSize(1200, 330);
		HBox weaponsDescribe = new HBox(20);
		weaponsDescribe.setMinSize(1200, 330);
		weaponsDescribe.setAlignment(Pos.CENTER);
		AnchorPane.setTopAnchor(weaponsDescribe, 60.0);
		AnchorPane.setBottomAnchor(titansDescribe, 0.0);
		AnchorPane.setTopAnchor(instructionsLabel, 0.0);
		//add titans based on code
		String Img;
		String currTitan;
		for(int i = 1; i < 5; i++) {
			switch(i) {
				case 1 : Img = "/pureTitanD.png";currTitan = "Pure Titan: Normal Titan that attacks once per turn." ;break;
				case 2 : Img = "/abnormalTitanD.png";currTitan = "Abnormal Titan: Performs the attack action twice per turn instead of once." ;break;
				case 3 : Img = "/armoredTitanD.png";currTitan = "Armored Titan: Takes only 25% of the intended damage.";break;
				default : Img = "/colossalTitanD.png";currTitan = "Colossal Titan: After every movement, Increases its speed stat by 1 “Distance Unit”." ;break;
			}
			AnchorPane titanContainer = new AnchorPane();
			//titanContainer.setMinSize(250, 500);
			titanContainer.setMaxSize(240, 290);
			ImageView titanview = new ImageView();
			Image titanImg = new Image(getClass().getResourceAsStream(Img));
			titanview.setImage(titanImg);
			Label titanDetails = new Label(currTitan);
			titanDetails.getStyleClass().add("titan-desc");
			titanDetails.setMaxWidth(250);
			AnchorPane.setTopAnchor(titanview,0.0);
			AnchorPane.setBottomAnchor(titanDetails,10.0);
			titanContainer.getChildren().addAll(titanview , titanDetails);
			titansDescribe.getChildren().add(titanContainer);
			
			switch(i) {
				case 1 : Img = "/PiercingCannonD.png";currTitan = "Piercing Cannon: Attacks the closest 5 titans to the wall on the weapon’s lane." ;break;
				case 2 : Img = "/SniperCannonD.png";currTitan = "Sniper Cannon: Attacks the first closest titan to the wall on the weapon’s lane" ;break;
				case 3 : Img = "/VolleySpreadCannonD.png";currTitan = "Volley Spread Cannon: Attack all the titans in between the weapon’s min and max ranges on the weapon’s lane.";break;
				default : Img = "/WallTrapD.png";currTitan = "Wall Trap: Attacks only one titan that has already reached the walls" ;break;
			}
			AnchorPane weaponContainer = new AnchorPane();
			weaponContainer.setMaxSize(240, 290);
			ImageView weaponview = new ImageView();
			Image weaponImg = new Image(getClass().getResourceAsStream(Img));
			weaponview.setImage(weaponImg);
			Label weaponDetails = new Label(currTitan);
			weaponDetails.getStyleClass().add("titan-desc");
			weaponDetails.setMaxWidth(250);
			AnchorPane.setTopAnchor(weaponview,0.0);
			AnchorPane.setBottomAnchor(weaponDetails,10.0);
			weaponContainer.getChildren().addAll(weaponview , weaponDetails);
			weaponsDescribe.getChildren().add(weaponContainer);
		}
			
		Button goButton = new Button();
		goButton.setText("Start");
		goButton.getStyleClass().add("StartButton");
		goButton.setMinSize(60, 30);
		goButton.setLayoutX(560);
		goButton.setLayoutY(380);
		goButton.setOnMouseClicked(event->{
			difficulty();
		});
		instructionsPane.getChildren().addAll(instructionBack,weaponsDescribe,titansDescribe,instructionsLabel,goButton);
		Scene instr = new Scene(instructionsPane,1200,750);
		instr.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(instr);
		stage.show();
		
	}

	
	private void difficulty() {
		HBox dif = new HBox();
		dif.setPrefWidth(1200);
		dif.setPrefHeight(750);
		
		// Hard
		AnchorPane hardR = new AnchorPane();
		hardR.setPrefWidth(600);
		hardR.setPrefHeight(750);
		ImageView hardView = new ImageView();
		Image hardImg = new Image(getClass().getResourceAsStream("/HardE.png"));
		hardView.setImage(hardImg);
		hButton = editModeButton(hButton,"Hard");
		hardR.getStyleClass().add("anchors");
		hardR.setId("hardR");
		Label lhard = new Label();
		lhard.setLayoutX(105);
		lhard.setLayoutY(300);
		lhard.setPrefWidth(390);
		lhard.setText("In this tower defense game, you must strategically defend your walls "
				+"from waves of enemies by deploying weapons along 5 parallel lanes.\n Initial Resources: 625"  );
		lhard.setVisible(false);
		hButton.setOnMouseEntered(event -> lhard.getStyleClass().add("description"));
		hButton.setOnMouseExited(event -> lhard.getStyleClass().remove("description"));
		
		hButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				play(5);
			}
		});
		hButton.getStyleClass().add("difficulty-button");
		
		hardR.getChildren().addAll(hardView,hButton,lhard);
		//Easy
		AnchorPane easyR = new AnchorPane();
		easyR.setPrefWidth(600);
		easyR.setPrefHeight(750);
		
		ImageView easyView = new ImageView();
		Image easyImg = new Image(getClass().getResourceAsStream("/EasyE.png"));
		easyView.setImage(easyImg);
		eButton = editModeButton(eButton,"Easy");
		easyR.getStyleClass().add("anchors");
		easyR.setId("easyR");
		Label leasy = new Label();
		leasy.setLayoutX(105);
		leasy.setLayoutY(300);
		leasy.setPrefWidth(390);
		leasy.setText("In this tower defense game, you must strategically defend your walls "
				+"from waves of enemies by deploying weapons along 3 parallel lanes.\n Initial Resources: 750"  );
		eButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				play(3);
			}
		});
		leasy.setVisible(false);
		eButton.setOnMouseEntered(event -> leasy.getStyleClass().add("description"));
		eButton.setOnMouseExited(event -> leasy.getStyleClass().remove("description"));
		eButton.getStyleClass().add("difficulty-button");
		
		easyR.getChildren().addAll(easyView,eButton,leasy);
		dif.getChildren().addAll(easyR,hardR);
		Mode = new Scene(dif,1200,750);
		Mode.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(Mode);
		stage.show();
	}
	
	
	private void play(int i) {
		mediaPlayerHome.stop();
		String musicFilePath = getClass().getResource("/determination.mp3").toExternalForm();
        Media media = new Media(musicFilePath);
        mediaPlayerBattle = new MediaPlayer(media);
        mediaPlayerBattle.setVolume(5.0);
        mediaPlayerBattle.setAutoPlay(true);
        mediaPlayerBattle.setOnEndOfMedia(() -> mediaPlayerBattle.seek(javafx.util.Duration.ZERO));
		
		heightLanes = (i == 3)? 230: 134;
		int widthLanes = (i == 3)? 800: 830;
		widthWall = (i == 3)? 175: 155;
		lanesAnchor = createLaneArray(i,widthLanes,heightLanes);
		wallsAnchor = createWallArray(i,widthWall,heightLanes);
		
		createLaneButtons(i);
		laneButtonMenu = new VBox(30);
		laneButtonMenu.setPrefWidth(widthWall);
		laneButtonMenu.setPrefHeight(750);
		for(int k = 0; k<i;k++)
			laneButtonMenu.getChildren().add(laneButtons[k]);
		laneButtonMenu.setVisible(false);
		
		createWeaponSpace(wallsAnchor);
		String modeimg = (i==3)? "/3lane.png":"/5lane.png";
		ImageView map = new ImageView();
		Image mapimg = new Image(getClass().getResourceAsStream(modeimg));
		map.setImage(mapimg);
		int initalResourcesPerLane = (i==3)? 250:125;
		int spawnDistance = widthLanes - 150;
		try {
			battle = new Battle(1,0,spawnDistance,i,initalResourcesPerLane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		VBox weaponBar = new VBox(5);
		weaponBar.setPrefWidth(390);
		weaponBar.setPrefHeight(550);
		
		ImageView wpv = new ImageView();
		Image wp = new Image(getClass().getResourceAsStream("/PiercingCannon.png"));
		wpv.setImage(wp);
		wpv.setFitWidth(100);
		wpv.setFitHeight(100);
		String name = battle.getWeaponFactory().getWeaponShop().get(1).getName();
		int price = battle.getWeaponFactory().getWeaponShop().get(1).getPrice();
		int damage = battle.getWeaponFactory().getWeaponShop().get(1).getDamage();
		piercingCannon = new Button("Name: "+name +"\nType: Piercing Cannon" +"\nPrice: "+price +" \nDamage: "+ damage,wpv);
		piercingCannon.setOnMouseClicked(event->{
			finalizePurchase(1);
		});
		piercingCannon.getStyleClass().add("weapon-button");
		piercingCannon.setTextAlignment(TextAlignment.CENTER);
		
		
		ImageView wsv = new ImageView();
		Image ws = new Image(getClass().getResourceAsStream("/SniperCannon.png"));
		wsv.setImage(ws);
		wsv.setFitWidth(100);
		wsv.setFitHeight(100);
		name = battle.getWeaponFactory().getWeaponShop().get(2).getName();
		price = battle.getWeaponFactory().getWeaponShop().get(2).getPrice();
		damage = battle.getWeaponFactory().getWeaponShop().get(2).getDamage();
		sniperCannon = new Button("Name: "+name +"\nType: Sniper Cannon" +"\nPrice: "+price +" \nDamage: "+ damage,wsv);
		sniperCannon.setOnMouseClicked(event->{
			finalizePurchase(2);
		});
		sniperCannon.getStyleClass().add("weapon-button");
		sniperCannon.setTextAlignment(TextAlignment.CENTER);
		
		
		ImageView wvv = new ImageView();
		Image wv = new Image(getClass().getResourceAsStream("/VolleySpreadCannon.png"));
		wvv.setImage(wv);
		wvv.setFitWidth(100);
		wvv.setFitHeight(100);
		name = battle.getWeaponFactory().getWeaponShop().get(3).getName();
		price = battle.getWeaponFactory().getWeaponShop().get(3).getPrice();
		damage = battle.getWeaponFactory().getWeaponShop().get(3).getDamage();
		volleySpreadCannon = new Button("Name: "+name +"\nType: Volley Spread Cannon" +"\nPrice: "+price +" \nDamage: "+ damage,wvv);
		volleySpreadCannon.setOnMouseClicked(event->{
			finalizePurchase(3);
		});
		volleySpreadCannon.getStyleClass().add("weapon-button");
		volleySpreadCannon.setTextAlignment(TextAlignment.CENTER);
		
		
		ImageView wwv = new ImageView();
		Image ww = new Image(getClass().getResourceAsStream("/WallTrap.png"));
		wwv.setImage(ww);
		wwv.setFitWidth(90);
		wwv.setFitHeight(100);
		name = battle.getWeaponFactory().getWeaponShop().get(4).getName();
		price = battle.getWeaponFactory().getWeaponShop().get(4).getPrice();
		damage = battle.getWeaponFactory().getWeaponShop().get(4).getDamage();
		wallTrap = new Button("Name: "+name +"\nType: Wall Trap" +"\nPrice: "+price +" \nDamage: "+ damage,wwv);
		wallTrap.setOnMouseClicked(event->{
			finalizePurchase(4);
		});
		wallTrap.getStyleClass().add("weapon-button");
		wallTrap.setTextAlignment(TextAlignment.CENTER);
		
		passButton = new Button("Pass Turn");
		passButton.setTextAlignment(TextAlignment.CENTER);
		passButton.getStyleClass().add("weapon-button");
		passButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				pass();
			}
		});
		automate = new Button("Automate");
		automate.getStyleClass().add("weapon-button");
		automate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				piercingCannon.setDisable(true);
				sniperCannon.setDisable(true);
				volleySpreadCannon.setDisable(true);
				wallTrap.setDisable(true);
				passButton.setDisable(true);
				automate.setText((!flag)?"Stop Automation":"Automate");
				flag = !flag; 
				automate(flag);
			}
		});
		HBox passmate = new HBox(10);
		passmate.getChildren().addAll(passButton,automate);
		HBox.setHgrow(passButton, Priority.ALWAYS);
		HBox.setHgrow(automate, Priority.ALWAYS);
		VBox.setVgrow(piercingCannon, Priority.ALWAYS);
        VBox.setVgrow(sniperCannon, Priority.ALWAYS);
        VBox.setVgrow(volleySpreadCannon, Priority.ALWAYS);
        VBox.setVgrow(wallTrap, Priority.ALWAYS);
        VBox.setVgrow(passmate, Priority.ALWAYS);
        passButton.setMaxWidth(Double.MAX_VALUE);
        passButton.setMaxHeight(35.0);
        piercingCannon.setMaxWidth(Double.MAX_VALUE);
        sniperCannon.setMaxWidth(Double.MAX_VALUE);
        volleySpreadCannon.setMaxWidth(Double.MAX_VALUE);
        wallTrap.setMaxWidth(Double.MAX_VALUE);
        piercingCannon.setMaxHeight(Double.MAX_VALUE);
        sniperCannon.setMaxHeight(Double.MAX_VALUE);
        volleySpreadCannon.setMaxHeight(Double.MAX_VALUE);
        wallTrap.setMaxHeight(Double.MAX_VALUE);
		weaponBar.getChildren().addAll(piercingCannon,sniperCannon,volleySpreadCannon,wallTrap,passmate);
		
		scoreLabel = new Label("Score: ");
		scoreLabel.getStyleClass().add("side-label");
		turnLabel = new Label("Turn: ");
		turnLabel.getStyleClass().add("side-label");
		phaseLabel = new Label("Phase: ");
		phaseLabel.getStyleClass().add("side-label");
		resourceLabel = new Label("Resources: ");
		resourceLabel.getStyleClass().add("side-label");
		resourceLabel.setLayoutY(560);
		scoreLabel.setLayoutY(610);
		phaseLabel.setLayoutY(660);
		turnLabel.setLayoutY(710);
		AnchorPane.setLeftAnchor(scoreLabel, 10.0);
		AnchorPane.setLeftAnchor(turnLabel, 10.0);
		AnchorPane.setLeftAnchor(phaseLabel, 10.0);
		AnchorPane.setLeftAnchor(resourceLabel, 10.0);
		
		AnchorPane sidebar = new AnchorPane(weaponBar,phaseLabel,turnLabel,resourceLabel,scoreLabel);
		sidebar.setPrefWidth(195);
		sidebar.setPrefHeight(750);
		AnchorPane.setRightAnchor(sidebar, 5.0);
		AnchorPane.setTopAnchor(sidebar, 5.0);
		sidebar.getStyleClass().add("sidebar");
		
		AnchorPane div = new AnchorPane();
		div.getChildren().addAll(map,sidebar);
		for(AnchorPane a: wallsAnchor) 
			div.getChildren().add(a);			
		for(AnchorPane a: lanesAnchor) 
			div.getChildren().add(a);	
		div.getChildren().addAll(laneButtons[0],laneButtons[1],laneButtons[2],buyLane4,buyLane5);
		
		Label msgP = new Label("Please choose a weapon to\n deploy or pass turn");
		msgP.getStyleClass().add("popup");
		Popup msg = new Popup();
		msg.getContent().add(msgP);
		msg.setAutoHide(true);
		msg.show(stage);
		
		insufficient = new Popup();
		Label errorLabel = new Label();
		errorLabel.getStyleClass().add("error-message");
		Button errorButton = new Button();
		errorButton.getStyleClass().add("error-button");
		errorButton.setText("Cancel");
		errorButton.setLayoutX(50);
		errorButton.setLayoutY(50);
		errorButton.setOnMouseClicked(event->{
			insufficient.hide();
		});
		AnchorPane insufficientPane = new AnchorPane();
		insufficientPane.setMinWidth(600);
		insufficientPane.setMinHeight(450);
		AnchorPane.setTopAnchor(errorLabel, 20.0);
		AnchorPane.setLeftAnchor(errorLabel, 133.0);
		AnchorPane.setLeftAnchor(errorButton, 245.0);
		AnchorPane.setBottomAnchor(errorButton, 100.0);
		insufficientPane.getChildren().addAll(errorLabel,errorButton);
		insufficient.getContent().add(insufficientPane);
		//insufficient.setAutoHide(true);
		
		Scene sx = new Scene(div,1400,750);
		sx.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(sx);
		stage.show();
		
		
	}
	private void purchase(int weaponCode, Button button) {
		ArrayList<Lane> lanes = battle.getOriginalLanes();
		int i = 0;
		Lane lane = lanes.get(i);
		for(Button curr: laneButtons) {
			if(button == curr) {
				lane = lanes.get(i);
				break;
			}
			i++;
		}
		try {
			battle.purchaseWeapon(weaponCode, lane);
			update();
		} catch (InsufficientResourcesException e) {
			AnchorPane a = (AnchorPane) insufficient.getContent().get(0);
			Label err =(Label) a.getChildren().get(0);
			err.setText("Not Enough Resources");
			insufficient.show(stage);
		} catch (InvalidLaneException e) {
			Label err = (Label) insufficient.getContent().get(0);
			err.setText("Invalid Lane");
		}		
	}
	
	
	private void finalizePurchase(int weaponCode) {
		ArrayList<Lane> lanes = battle.getOriginalLanes();
		int h = heightLanes + ((lanes.size() == 3)?30:20);
		double z;
		for(int i = 0; i < laneButtons.length;i++) {
			if(!lanes.get(i).isLaneLost()) {
				Button current = laneButtons[i];
				current.setLayoutX(widthWall/2);
				z = current.getHeight()/2;
				current.setLayoutY((heightLanes/2 + (h*i))-z);
				current.setVisible(true);
				current.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent event) {
						purchase(weaponCode,(Button)event.getSource());
					}
				});
			}
		}
	}
	
	
 	private AnchorPane[] createLaneArray(int i, int width, int height) {
		AnchorPane[] x = new AnchorPane[i];
		int shift = (i == 3)? 30:20;
		int u = (i == 3)? 200:170;
		for(int j = 0; j< i; j++) {
			AnchorPane z = new AnchorPane();
			z.setPrefHeight(height);
			z.setPrefWidth(width);
			z.setLayoutX(u);
			z.setLayoutY((height + shift)*j);
			//z.getStyleClass().add("bak2");
			x[j] = z;
		}
		return x;
	}
	
	private AnchorPane[] createWeaponSpace(AnchorPane[] walls) {
		int size = walls.length;
		int width = (size == 3) ? 85:75;
		int height = heightLanes;
		for(int i = 0; i<size;i++){
			GridPane x = new GridPane();
			AnchorPane.setRightAnchor(x, 0.0);
			x.setHgap(2); 
	        x.setVgap(2);
	        x.setMinWidth(width);
			x.setMinHeight(height);
			//x.getStyleClass().add("bak2");
			walls[i].getChildren().addAll(x);
		}
		return walls;
	}
	
	private AnchorPane[] createWallArray(int i,int width, int height){
		AnchorPane[] x = new AnchorPane[i];
		int shift = (i == 3)? 30:20;
		int shiftx = (i == 3)? 90:45;
		int shifty = (i == 3)? 100:55;
		for(int j = 0; j< i; j++) {
			AnchorPane z = new AnchorPane();
			z.setMinWidth(width);
			z.setMinHeight(height);
			z.setLayoutY((height+shift)*j);
			StackPane healthBarContainer = new StackPane();
	        healthBarContainer.getStyleClass().add("health-bar-container");
	        

	        Rectangle backgroundBar = new Rectangle(height - 20, 20);
	        backgroundBar.getStyleClass().add("health-bar-background");

	        Rectangle healthBar = new Rectangle(height- 20, 20);
	        healthBar.getStyleClass().add("health-bar");
	        Label health = new Label("health");
	        health.getStyleClass().add("health-points");
	        Label dangerLabel = new Label("Danger Level: ");
			dangerLabel.getStyleClass().add("danger-level");
			dangerLabel.setMaxWidth(width);
			dangerLabel.setMaxHeight(20);
			dangerLabel.setMaxSize(width, 20);
			dangerLabel.setRotate(270);
			
			int d = (i==3)? -shiftx+70:-shiftx + 25;
	        healthBarContainer.getChildren().addAll(backgroundBar, healthBar, health);
	        healthBarContainer.setLayoutX(-shiftx);
	        healthBarContainer.setLayoutY(shifty);
	        dangerLabel.setLayoutX(d);
	        dangerLabel.setLayoutY(shifty);
			//z.getStyleClass().add("bak");
			z.getChildren().addAll(healthBarContainer,dangerLabel);
			x[j] = z;
		}
		return x;
	}
	
	private void createLaneButtons(int i) {
		laneButtons = new Button[i];
		buyLane1 = new Button();
		buyLane1.setText("Buy");
		buyLane1.getStyleClass().add("weapon-button");
		buyLane1.setVisible(false);
		laneButtons[0] = buyLane1;
		buyLane2 = new Button();
		buyLane2.setText("Buy");
		buyLane2.getStyleClass().add("weapon-button");
		buyLane2.setVisible(false);
		laneButtons[1] = buyLane2;
		buyLane3 = new Button();
		buyLane3.setText("Buy");
		buyLane3.getStyleClass().add("weapon-button");
		buyLane3.setVisible(false);
		laneButtons[2] = buyLane3;
		buyLane4 = new Button();
		buyLane4.setText("Buy");
		buyLane4.getStyleClass().add("weapon-button");
		buyLane4.setVisible(false);
		buyLane5 = new Button();
		buyLane5.setText("Buy");
		buyLane5.getStyleClass().add("weapon-button");
		buyLane5.setVisible(false);
		if(i==5) {
			laneButtons[3] = buyLane4;
			laneButtons[4] = buyLane5;
		}
	}
	
	private void update() {
		for(Button cur: laneButtons)
			cur.setVisible(false);
		scoreLabel.setText("Score: "+battle.getScore());
		turnLabel.setText("Turn: "+battle.getNumberOfTurns());
		phaseLabel.setText("Phase: "+battle.getBattlePhase());
		resourceLabel.setText("Resources: "+battle.getResourcesGathered());
		ArrayList<Lane> lanes = battle.getOriginalLanes();
		for(AnchorPane a: lanesAnchor)
			a.getChildren().clear();
		if(battle.isGameOver())
			gameOver();
		Random random = new Random();
		int i = 0;
		int[][] yaxis3 = {{65,0,65,0},{0,57,115,170}};
		for(Lane lane: lanes) {
			if(!lane.isLaneLost()) {
				PriorityQueue<Titan> titans = lane.getTitans();
				int selector = (lanesAnchor.length == 3)?1:0;
				for(Titan titan: titans) {
					int index = random.nextInt(4);
					int yPosition = yaxis3[selector][index];
					StackPane titanContainer = new StackPane();
					ImageView titanview = new ImageView();
					String Img = titanString(titan);
					Image titanImg = new Image(getClass().getResourceAsStream(Img));
					titanview.setImage(titanImg);
					Label titanHealth = new Label(titan.getCurrentHealth()+"");
					titanHealth.getStyleClass().add("titan");
					int distance = titan.getDistance();
					titanContainer.setLayoutX(distance);
					titanContainer.setLayoutY(yPosition);
					
					StackPane healthBarContainer = new StackPane();
			        healthBarContainer.getStyleClass().add("health-bar-container");
			        
			        Rectangle backgroundBar = new Rectangle(titanImg.getWidth()-10, 10);
			        backgroundBar.getStyleClass().add("health-bar-background-titan");
			        
			        Rectangle healthBar = new Rectangle(titanImg.getWidth()-10, 10);
			        healthBar.getStyleClass().add("health-bar-titan");
			        titanHealth.getStyleClass().add("health-points-titan");
			        int originalHealth = titan.getBaseHealth();
					int currentHealth = titan.getCurrentHealth();
					double percent =(double) currentHealth/originalHealth;
					titanHealth.setText("" + currentHealth);
					healthBar.setWidth((titanImg.getWidth()-10)*percent);
					healthBar.setTranslateX(((titanImg.getWidth()-10) - healthBar.getWidth()) / 2);
					healthBarContainer.setMaxWidth((titanImg.getWidth()-10));
					healthBarContainer.setMaxHeight(15);
					healthBarContainer.getChildren().addAll(backgroundBar,healthBar,titanHealth);
					
					titanContainer.getChildren().addAll(titanview,healthBarContainer);
					lanesAnchor[i].getChildren().add(titanContainer);
				}
				
				ArrayList<Weapon> weapons = lane.getWeapons();
				int pierce = countWeapons(weapons, 1);
				int sniper = countWeapons(weapons, 2);
				int volley = countWeapons(weapons, 3);
				int trap = countWeapons(weapons, 4);
				for(Weapon weapon: weapons) {
					int w = weaponCode(weapon);
					int current = 0;
					switch(w){
						case 1:current = pierce;break;
						case 2:current = sniper;break;
						case 3:current = volley;break;
						default:current = trap;break;
					}
					if(current == 1) {
						GridPane grid =(GridPane) wallsAnchor[i].getChildren().get(2);
						
						StackPane weaponContainer = new StackPane();
						ImageView weaponview = new ImageView();
						int weaponDimension = (lanes.size() == 3)?57:33;
						weaponview.setFitWidth(weaponDimension);
						weaponview.setFitHeight(weaponDimension);
						
						String Img = weaponString(weapon);
						Image titanImg = new Image(getClass().getResourceAsStream(Img));
						weaponview.setImage(titanImg);
						weaponContainer.getChildren().add(weaponview);
						grid.add(weaponContainer, 0,w-1);
					}
					Label counter = new Label("x"+current);
					counter.getStyleClass().add("weapon-counter");
					//GridPane.setConstraints(counter, 1, w-1);
					GridPane grid =(GridPane) wallsAnchor[i].getChildren().get(2);
					grid.add(counter,1 ,w-1);
				}
			}else {
				ImageView wallView = new ImageView();
				String name = (battle.getOriginalLanes().size()==3)?"/wallDest3.png":"/wallDest5.png";
				Image wallImage = new Image(getClass().getResourceAsStream(name));
				wallView.setImage(wallImage);
				wallView.setFitHeight(heightLanes);
				wallView.setFitWidth(widthWall+30);
				wallsAnchor[i].getChildren().add(wallView);
			}
			int dangerLevel = (lane.isLaneLost())?0:lane.getDangerLevel();
			ObservableList<Node> children = wallsAnchor[i].getChildren();
			Label f = (Label) children.get(1);
			f.setText("Danger Level: "+dangerLevel);
			int originalHealth = lane.getLaneWall().getBaseHealth();
			int currentHealth = lane.getLaneWall().getCurrentHealth();
			double percent =(double) currentHealth/originalHealth;
			StackPane currentBar = (StackPane)wallsAnchor[i].getChildren().get(0);
			Label healthBarLabel = (Label) currentBar.getChildren().get(2);
			healthBarLabel.setText("Health: " + currentHealth);
			Rectangle bar = (Rectangle) currentBar.getChildren().get(1);
			bar.setWidth((heightLanes-20)*percent);
			bar.setTranslateY(((heightLanes-20) - bar.getWidth()) / 2);
			i++;
		}
		
	}
	private int countWeapons(ArrayList<Weapon> weapons, int w) {
		int i = 0;
		for(Weapon weapon: weapons) {
			if(w == weaponCode(weapon)) 
				i++;
		}
		return i;
	}
	
	private String titanString(Titan t) {
		if(t instanceof PureTitan)
			return "/pureTitan.png";
		if(t instanceof ArmoredTitan)
			return "/armoredTitan.png";
		if(t instanceof ColossalTitan)
			return "/colossalTitan.png";
		return "/abnormalTitan.png";
	}
	
	private String weaponString(Weapon w) {
		if(w instanceof PiercingCannon)
			return "/PiercingCannon.png";
		if(w instanceof SniperCannon)
			return "/SniperCannon.png";
		if(w instanceof VolleySpreadCannon)
			return "/VolleySpreadCannon.png";
		return "/WallTrap.png";
	}
	private int weaponCode(Weapon w) {
		if(w instanceof PiercingCannon)
			return 1;
		if(w instanceof SniperCannon)
			return 2;
		if(w instanceof VolleySpreadCannon)
			return 3;
		return 4;
	}
	private void pass() {
		battle.passTurn();
		update();
	}
	
	private void gameOver() {
		piercingCannon.setDisable(true);
		sniperCannon.setDisable(true);
		volleySpreadCannon.setDisable(true);
		wallTrap.setDisable(true);
		passButton.setDisable(true);
		automate.setDisable(true);
		
		Popup endGameP = new Popup();
		endGameP.setWidth(800);
		endGameP.setHeight(450);
		Button returnMenu = new Button();
		returnMenu.setText("Return to Main Menu");
		returnMenu.getStyleClass().add("return-button");
		returnMenu.setMaxWidth(205);
		returnMenu.setLayoutX(297);
		returnMenu.setOnMouseClicked(event->{
			mediaPlayerBattle.stop();
			try {
				start(stage);
				endGameP.hide();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		AnchorPane.setBottomAnchor(returnMenu, 100.0);
		AnchorPane endGame = new AnchorPane();
		endGame.setPrefSize(800, 450);
		Label endGameLabel = new Label("Game Over\nYou Lost");
		endGameLabel.getStyleClass().add("end-game-label");
		endGameLabel.setMinWidth(170);
		endGameLabel.setLayoutX(270);
		Label score = new Label("Score: " + battle.getScore() + "\nTurns: " + battle.getNumberOfTurns());
		score.getStyleClass().add("end-game-score");
		score.setLayoutY(180);
		score.setLayoutX(315);
		endGame.getChildren().addAll(returnMenu , endGameLabel, score);
		endGameP.getContent().addAll(endGame);
		endGameP.show(stage);
		
	}
	
	
	private void automate(boolean flag1) {
		PauseTransition p = new PauseTransition(Duration.seconds(0.5));
		if (battle.getLanes().peek() == null || battle.getLanes().peek().getTitans().peek() == null) {
			Lane lane = (battle.getLanes().peek() == null) ? battle.getOriginalLanes().get(0)
					: battle.getLanes().peek();
			try {
				battle.purchaseWeapon(1, lane);
			} catch (InsufficientResourcesException | InvalidLaneException e) {
				e.printStackTrace();
			}
		} else {

			// Lane mostDanger = battle.getLanes().peek();
			int res = battle.getResourcesGathered();
			int cheapest = battle.getWeaponFactory().getWeaponShop().get(1).getPrice();
			int price3 = battle.getWeaponFactory().getWeaponShop().get(3).getPrice();
			int price4 = battle.getWeaponFactory().getWeaponShop().get(4).getPrice();
			for (int i = 2; i < battle.getWeaponFactory().getWeaponShop().size(); i++) {
				cheapest = (cheapest < battle.getWeaponFactory().getWeaponShop().get(i).getPrice()) ? cheapest
						: battle.getWeaponFactory().getWeaponShop().get(i).getPrice();
			}
			if (cheapest > battle.getResourcesGathered())
				pass();
			else if (res >= cheapest && res < price4) {
				auto2();
			} else if (res >= price4 && res < price3)
				auto3();
			else
				auto4();
		}
		update();
		if (!battle.isGameOver()) {
			p.setOnFinished(event -> automate(flag));
			p.play();
		}
		if (!flag1) {
			p.stop();
			piercingCannon.setDisable(false);
			sniperCannon.setDisable(false);
			volleySpreadCannon.setDisable(false);
			wallTrap.setDisable(false);
			passButton.setDisable(false);
		}
	}
	
	
	
	private void auto2() {
		if(battle.getLanes().peek().getTitans().size()>= 5) {
			try {
				battle.purchaseWeapon(1, battle.getLanes().peek());
			} catch (InsufficientResourcesException | InvalidLaneException e) {
				e.printStackTrace();
			}
		}else {
			try {
				battle.purchaseWeapon(2, battle.getLanes().peek());
			} catch (InsufficientResourcesException | InvalidLaneException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void auto3() {
		if(battle.getLanes().peek().getTitans().peek().hasReachedTarget()) {
			try {
				battle.purchaseWeapon(4, battle.getLanes().peek());
			} catch (InsufficientResourcesException | InvalidLaneException e) {
				e.printStackTrace();
			}
		}else {
			auto2();
		}
	}

	
	private void auto4() {
		Lane mostDanger = battle.getLanes().peek();
		WeaponRegistry w = battle.getWeaponFactory().getWeaponShop().get(3);
		int i = 0;
		for(Titan titan: mostDanger.getTitans()) {
			if(titan.hasReachedTarget())
				break;
			else {
				int pos = titan.getDistance() + titan.getSpeed();
				if(pos >= w.getMinRange() && pos <= w.getMaxRange())
					i++;
			}
		}
		if(i == 0)
			auto3();
		else if(i > 5 && battle.getNumberOfTurns() < 60) {
			try {
				battle.purchaseWeapon(3, mostDanger);
			} catch (InsufficientResourcesException | InvalidLaneException e) {
				e.printStackTrace();
			}
		}
		else
			auto3();
		
	}
	
	public static void main(String []args) {
		launch(args);
	}
	
}
