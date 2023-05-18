<template>
  <div class="back">
    <div class="title">빌딩을 피해라</div>
    <div class="wrapper"></div>
  </div>
</template>

<script>
import * as PIXI from "pixi.js";
import bgUrl from "@/assetgame/img/bg3.png";
import groundURL from "@/assetgame/img/ground.png";
import pipeBottomURL from "@/assetgame/img/pipe-bottom.png";
import pipeTopURL from "@/assetgame/img/pipe-top.png";
import clueTapURL from "@/assetgame/img/clue-tap.png";

import charBirdImg1 from "@/assetgame/img/drags/flybird.png";
import charBirdImg2 from "@/assetgame/img/drags/flybird2.png";
import charBirdImg3 from "@/assetgame/img/drags/flybird.png";
import { useStore } from "@/stores/store";
import { countStore } from "@/stores/birdCountStore";

export default {
  name: "GameView",

  data() {
    return {
      gameStart: false,
      gameFinish: false,
      counterStart: 0,
      app: {
        game: null,
        width: 340,
        height: 600,
      },
      bird: {
        alienImages: [charBirdImg1, charBirdImg2, charBirdImg3],
        textureArray: [],
        animatedSpray: null,
        x: 45,
        y: 225,
      },
      pipe: {
        pipeTextureBottom: null,
        pipeSpriteBottom: null,
        pipeTextureTop: null,
        pipeSpriteTop: null,
        pipeDdistance: 80,
        x: 255,
        By: 300,
        Ty: 0,
        height: 251,
      },
      ground: {
        groundTexture: null, //
        tilingSpriteGround: null, //
        x: 0,
        y: 500,
      },
      clue: {
        textureClue: null,
        spriteClue: null,
        x: 123,
        y: 330,
      },
      scoreText: {
        style: null,
        text: "0",
        score: 0,
        x: 100,
        y: 30,
      },
      bgTexture: null, //
      tilingSpriteBG: null, //
      countSin: 0,
      jumping: 0,
    };
  },
  computed: {
    // computedTy() {
    //   return this.pipe.By - this.pipe.height - this.pipe.pipeDdistance
    // }
  },
  mounted() {
    this.drawPixi();
    this.listener();
  },
  methods: {
    drawPixi() {
      let wrapper = document.querySelector(".wrapper");
      this.app.game = new PIXI.Application({
        transparent: true,
        width: this.app.width,
        height: this.app.height,
        interactive: true, // hz
        buttonMode: true,
      });

      this.app.game.stage.on("click", () => {
        if (!this.gameFinish) {
          if (!this.gameStart) {
            this.start();
          } else {
            // this.moveBird()
            this.jumping = 8;
          }
        }
      });

      wrapper.appendChild(this.app.game.view);
      this.app.game.stage.interactive = true;

      this.addText();
      this.renderSourse();
      this.stageAdd();
      this.setPositionPipe();
      this.setPositionClue();
      this.setPositionScoreText();

      for (let i = 0; i < this.bird.alienImages.length; i++) {
        let texture = PIXI.Texture.from(this.bird.alienImages[i]);
        this.bird.textureArray.push(texture);
      }

      this.createdAnimatedSprite();

      this.app.game.ticker.add(() => {
        if (!this.gameFinish) {
          this.moveGround();
          this.moveBg();
          if (this.gameStart) {
            if (this.jumping) {
              this.moveBird();
            }
            if (this.jumping >= 50) {
              this.jumping = 0;
            }

            this.bird.animatedSpray.position.set(this.bird.x, this.bird.y);
            this.gravitiBird();
            this.movePipe();
            this.setPositionPipe();
            this.hitTopPipe();
            this.hitBottomPipe();
            // this.hitTop()
            this.hitGround();
            this.climbsBorders();
          }
        } else {
          this.bird.animatedSpray.position.set(this.bird.x, this.bird.y);
          this.circulationBird();
        }
      });
    },
    renderSourse() {
      this.bgTexture = PIXI.Texture.from(bgUrl);
      this.bgTexture.orig.width = 340;
      this.bgTexture.orig.height = 600;
      this.pipe.pipeTextureBottom = PIXI.Texture.from(pipeBottomURL);
      this.pipe.pipeSpriteBottom = PIXI.Sprite.from(
        this.pipe.pipeTextureBottom
      );
      this.pipe.pipeTextureTop = PIXI.Texture.from(pipeTopURL);
      this.pipe.pipeSpriteTop = PIXI.Sprite.from(this.pipe.pipeTextureTop);
      this.ground.groundTexture = PIXI.Texture.from(groundURL);
      this.clue.textureClue = PIXI.Texture.from(clueTapURL);
      this.clue.spriteClue = PIXI.Sprite.from(this.clue.textureClue);

      this.tilingSpriteBG = new PIXI.TilingSprite(this.bgTexture, 340, 600);
      this.ground.tilingSpriteGround = new PIXI.TilingSprite(
        this.ground.groundTexture,
        353,
        126
      );
      this.ground.tilingSpriteGround.position.set(this.ground.x, this.ground.y);
    },
    createdAnimatedSprite() {
      this.bird.animatedSpray = new PIXI.AnimatedSprite(this.bird.textureArray);
      this.bird.animatedSpray.position.set(this.bird.x, this.bird.y);
      this.app.game.stage.addChild(this.bird.animatedSpray);
      this.bird.animatedSpray.animationSpeed = 0.12;
      this.bird.animatedSpray.rotation;
      this.bird.animatedSpray.width = 27;
      this.bird.animatedSpray.height = 19;

      this.bird.animatedSpray.scale.x = 1;
      this.bird.animatedSpray.scale.y = 1;
      this.bird.animatedSpray.play();
    },
    addText() {
      this.scoreText.text = new PIXI.Text(this.scoreText.text, {
        fontFamily: "BF",
        fontSize: 30,
        fontWeight: "bold",
        fill: ["#ffffff"],
        stroke: "#000",
        strokeThickness: 5,
      });
    },
    stageAdd() {
      this.app.game.stage.addChild(this.tilingSpriteBG);
      this.app.game.stage.addChild(this.pipe.pipeSpriteBottom);
      this.app.game.stage.addChild(this.pipe.pipeSpriteTop);
      this.app.game.stage.addChild(this.ground.tilingSpriteGround);
      this.app.game.stage.addChild(this.clue.spriteClue);

      this.app.game.stage.addChild(this.scoreText.text);
    },
    setPositionPipe() {
      this.pipe.pipeSpriteBottom.position.set(this.pipe.x, this.pipe.By);
      this.pipe.pipeSpriteTop.position.set(
        this.pipe.x,
        this.pipe.By - this.pipe.height - this.pipe.pipeDdistance
      );
    },
    setPositionClue() {
      this.clue.spriteClue.position.set(this.clue.x, this.clue.y);
    },
    setPositionText() {
      this.clue.spriteClue.position.set(this.clue.x, this.clue.y);
    },
    setPositionScoreText() {
      const width = this.app.width / 2 - 10;
      this.scoreText.text.position.set(width, this.scoreText.y);
    },
    setScoreText() {
      this.scoreText.score++;
      this.scoreText.text.text = `${this.scoreText.score}`;
    },
    gravitiBird() {
      if (this.bird.animatedSpray.rotation >= 1) {
        // this.bird.animatedSpray.rotation = this.bird.animatedSpray.rotation
      } else {
        this.bird.animatedSpray.rotation += 0.02;
      }
      this.bird.y += 2;
    },
    moveBird() {
      this.bird.animatedSpray.rotation = -0.6;
      this.bird.y -= 7;
      this.jumping += 6;
    },
    circulationBird() {
      this.countSin += 0.001;
      if (this.countSin > 0.1) {
        this.countSin = 0;
      } else {
        this.bird.animatedSpray.rotation += 0.1;
        this.bird.animatedSpray.scale.x += Math.sin(this.countSin);
        this.bird.animatedSpray.scale.y += Math.sin(this.countSin);
      }
      if (this.bird.y > 200) {
        this.bird.y -= 10;
      } else {
        this.bird.y -= 4;
      }
      this.bird.x += 2;
    },
    moveBg() {
      this.tilingSpriteBG.tilePosition.x -= 1.2;
    },

    moveGround() {
      this.ground.tilingSpriteGround.tilePosition.x -= 2.2;
    },
    movePipe() {
      this.pipe.Ty = this.pipe.By - this.pipe.height - this.pipe.pipeDdistance;

      // if (this.pipe.x < this.bird.x && this.pipe.x > this.bird.x - 2) {
      //   this.setScoreText()
      // }
      if (this.scoreText.score > 3) {
        if (this.pipe.x < this.bird.x && this.pipe.x > this.bird.x - 10) {
          this.setScoreText();
        }
      } else {
        if (this.pipe.x < this.bird.x && this.pipe.x > this.bird.x - 4) {
          this.setScoreText();
        }
      }

      if (this.pipe.x <= -42) {
        this.pipe.x = 340;
        this.pipe.By = this.randomHeight();
      }
      if (this.scoreText.score > 3) {
        this.pipe.x -= 5;
      } else {
        this.pipe.x -= 3;
      }
    },
    hitTopPipe() {
      let birdTop = this.bird.y;
      let birdRight = this.bird.x + this.bird.animatedSpray.width;
      let birdBottom = this.bird.y + this.bird.animatedSpray.height;
      let birdLeft = this.bird.x;
      let topPipeTop =
        this.pipe.By - this.pipe.height - this.pipe.pipeDdistance;
      let topPipeRight = this.pipe.x + this.pipe.pipeSpriteTop.width;
      let topPipeBottom = this.pipe.Ty + this.pipe.pipeSpriteTop.height;
      let topPipeLeft = this.pipe.x;

      if (
        birdTop < topPipeBottom &&
        birdRight > topPipeLeft &&
        birdLeft < topPipeRight &&
        birdBottom > topPipeTop
      ) {
        this.finish();
      }
    },
    hitBottomPipe() {
      let birdTop = this.bird.y;
      let birdRight = this.bird.x + this.bird.animatedSpray.width;
      let birdBottom = this.bird.y + this.bird.animatedSpray.height;
      let birdLeft = this.bird.x;

      let bottomPipeTop = this.pipe.By;
      let bottomPipeRight = this.pipe.x + this.pipe.pipeSpriteBottom.width;
      let bottomPipeBottom = this.pipe.By + this.pipe.pipeSpriteBottom.height;
      let bottomPipeLeft = this.pipe.x;

      if (
        birdTop < bottomPipeBottom &&
        birdRight > bottomPipeLeft &&
        birdLeft < bottomPipeRight &&
        birdBottom > bottomPipeTop
      ) {
        this.finish();
      }
    },
    // hitTop() {
    //   let birdBottom = this.bird.y + this.bird.animatedSpray.height;
    //   let fieldTop = this.ground.tilingSpriteGround.y

    // },
    hitGround() {
      let birdBottom = this.bird.y + this.bird.animatedSpray.height;
      let groundTop = this.ground.tilingSpriteGround.y;

      if (birdBottom > groundTop) {
   
        this.finish();
      }
    },
    climbsBorders() {
      let birdTop = this.bird.y;
      if (birdTop <= 0) {
        this.finish();
      }
    },

    start() {
      this.gameStart = !this.gameStart;
      this.app.game.stage.removeChild(this.clue.spriteClue);
    },
    finish() {
      const store = countStore();

      store.increment();
      const score = useStore();
      this.gameStart = !this.gameStart;
      this.gameFinish = true;
      this.app.game.stage.removeChild(this.clue.spriteClue);
      score.$patch({ score: this.scoreText.score });
      if(this.$route.name=="gameView"){
        setTimeout(() => this.$router.push({ name: "gameover" }), 1000);
      }
    },
    chekGame() {
      if (!this.gameStart) {
        this.start();
      } else {
        this.moveBird();
      }
    },
    listener() {
        window.addEventListener("keydown", (e) => {
          if (e.key === "ArrowUp" || e.code === "Space") {
            this.chekGame();
          }
        });
        window.addEventListener("touchend", () => {
          if(this.$route.name=="gameView"){
            this.chekGame();
          }
        });
    },
    randomHeight() {
      return Math.round(Math.random() * (350 - 220) + 220);
    },
  },
};
</script>

<style lang="scss" scoped>
.back {
  background-color: #f7fbf3;
}
.title {
  padding-top: 20px;
  font-family: "NeoDunggeunmo";
  font-size: 3vw;
  font-weight: 700;
  color: black;
}
.wrapper {
  flex-grow: 1;
  width: 345px;
  height: 600px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 50px;
  margin-left: 40%;
}

@media (max-width: 600px) {
  .wrapper {
    flex-grow: 1;
    width: 345px;
    height: 600px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 5% 5% 5% 5%;
  }
}
</style>
