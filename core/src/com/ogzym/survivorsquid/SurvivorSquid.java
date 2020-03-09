package com.ogzym.survivorsquid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorSquid extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture background2,background3,background4;
	Texture squid;
	Texture shark;
	Texture shark2;
	Texture shark3;
	float squidX = 0;
	float squidY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.8f;
	float enemyVelocity = 10;
	Random random;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	Circle squidCircle;

	ShapeRenderer shapeRenderer;

	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffSet = new float[numberOfEnemies];
	float [] enemyOffSet2 = new float[numberOfEnemies];
	float [] enemyOffSet3 = new float[numberOfEnemies];
	float distance = 0;

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		background2 = new Texture("mountains.png");
		background3= new Texture("trees.png");
		background4 = new Texture("trees2.png");
		squid = new Texture("squid.png");
		shark = new Texture("shark.png");
		shark2 = new Texture("shark.png");
		shark3 = new Texture("shark.png");

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();


		squidX = Gdx.graphics.getWidth() / 4 - squid.getHeight() / 2;
		squidY = Gdx.graphics.getHeight() / 3;

		shapeRenderer = new ShapeRenderer();


		squidCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];


		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);


		for (int i = 0; i<numberOfEnemies; i++) {


			enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - shark.getWidth() / 2 + i * distance;


			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}




	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(background2,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(background3,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(background4,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState ==1) {



			if (enemyX[scoredEnemy] < squidX-squid.getWidth()) {
				score++;

				if (scoredEnemy < numberOfEnemies - 1) {
					scoredEnemy++;
				} else {
					scoredEnemy = 0;
				}

			}



			if (Gdx.input.justTouched()) {

				velocity = -12;

			}


			for (int i = 0; i < numberOfEnemies; i++) {


				if (enemyX[i] < -Gdx.graphics.getWidth() / 8) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}


				batch.draw(shark,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet[i],Gdx.graphics.getWidth() / 8,Gdx.graphics.getHeight() / 10);
				batch.draw(shark2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth() / 8,Gdx.graphics.getHeight() / 10);
				batch.draw(shark3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth() / 8,Gdx.graphics.getHeight() / 10);


				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20,  Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 40);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20,  Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 40);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20,  Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 40);


			}



			if (squidY > 0) {
				velocity = velocity + gravity;
				squidY = squidY - velocity;
			} else {
				gameState = 2;
			}


		} else if (gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch,"Game Over! Tap To Play Again!",100,Gdx.graphics.getHeight() / 2);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				squidY = Gdx.graphics.getHeight() / 3;


				for (int i = 0; i<numberOfEnemies; i++) {


					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - shark.getWidth() / 2 + i * distance;


					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				velocity = 0;
				scoredEnemy = 0;
				score = 0;

			}
		}


		batch.draw(squid,squidX, squidY, Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		squidCircle.set(squidX +Gdx.graphics.getWidth() / 30 ,squidY + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 40);



		for ( int i = 0; i < numberOfEnemies; i++) {


			if (Intersector.overlaps(squidCircle,enemyCircles[i]) || Intersector.overlaps(squidCircle,enemyCircles2[i]) || Intersector.overlaps(squidCircle,enemyCircles3[i])) {
				gameState = 2;
			}
		}


	}

	@Override
	public void dispose () {

	}
}
