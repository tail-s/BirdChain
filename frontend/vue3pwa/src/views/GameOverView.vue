<template>
  <div class="back">
    <div class="title">
      매일 {{ count.birdcount }}마리의 새가 유리창 충돌로 죽고 있습니다.
    </div>
    <div class="game-over"></div>
  </div>
</template>

<script>
import * as PIXI from "pixi.js";
import groundURL from "@/assetgame/img/ground.png";
import buttonStartURL from "@/assetgame/img/start.png";
import gameOverImg from "@/assetgame/img/game-over.png";
import gshotBird from "@/assetgame/img/ghost-bird.png";
import pointer from "@/assetgame/img/pointer.png";
import { useStore } from "@/stores/store";
import { countStore } from "@/stores/birdCountStore";

export default {
  name: "GameOverView",

  data() {
    return {
      app: {
        game: null,
        width: 330,
        height: 600,
      },
      bird: {
        texture: null,
        sprite: null,
        x: 237,
        y: 8,
        width: 40,
        height: 30,
      },
      gameOver: {
        x: 0,
        y: 0,
        texture: null,
        sprite: null,
        width: 120,
        height: 48,
      },
      logoContainer: {
        container: null,
        x: 30,
        y: 225,
      },

      ground: {
        groundTexture: null,
        tilingSpriteGround: null,
        x: 0,
        y: 500,
      },
      buttonStart: {
        width: 96,
        height: 33,
        x: 120,
        y: 405,
        texture: null,
        sprite: null,
      },
      recordText: {
        style: null,
        text: "0",
        score: 0,
        x: 5,
        y: 5,
      },
      factor: 1,
    };
  },
  setup() {
    const store = countStore();

    const count = store;

    return {
      count,
    };
  },
  mounted() {
    this.drawPixi();
    this.listener();
  },
  computed: {
    // ...mapState({
    //   record: (state) => state.record,
    // }),
    isRecord() {
      const score = useStore();

      // const record = localStorage.getItem("record");
      // if (!record) {
      //   return 0;
      // } else {
      //   return record;
      // }
      const record = score.score;
      return record;
    },
  },
  methods: {
    startGame() {
      if(this.$route.name=="gameover"){
         this.$router.push({ name: "gameView" });
        }
    },
    drawPixi() {
      let gameOverWrapper = document.querySelector(".game-over");

      this.app.game = new PIXI.Application({
        transparent: true,
        width: this.app.width,
        height: this.app.height,
        cursor : 'cursor'
      });
      gameOverWrapper.appendChild(this.app.game.view);
      this.addText();

      this.setPositionRecordText();
      this.stageAdd();
      this.setCursorPointer();
      this.createdContainer();
      this.setPositionContainer();
      this.downloadTexture();
      this.createdSprites();
      this.addSpriteInContainer();
      this.addSpriteInGame();
      this.setPositionSpritesInContainer();
      this.setPositionSpritesInGame();
      this.buttonStart.sprite.interactive = true;
      this.buttonStart.sprite.buttonMode = true;

      this.buttonStart.sprite.on("click", () => this.startGame());

      this.app.game.ticker.add(() => {
        this.moveContainer();
        this.moveGround();
      });
    },

    createdContainer() {
      this.logoContainer.container = new PIXI.Container();
      this.app.game.stage.addChild(this.logoContainer.container);
      this.logoContainer.container.position.set(
        this.logoContainer.x,
        this.logoContainer.y
      );
    },
    setPositionContainer() {
      this.logoContainer.container.position.set(
        this.logoContainer.x,
        this.logoContainer.y
      );
    },
    moveContainer() {
      if (this.logoContainer.y >= 150) {
        this.factor = -1;
      } else if (this.logoContainer.y <= 140) {
        this.factor = 1;
      }
      this.logoContainer.y += 0.25 * this.factor;
      this.setPositionContainer();
    },
    stageAdd() {
      this.app.game.stage.addChild(this.recordText.text);
    },
    addText() {
      this.recordText.text = new PIXI.Text(`record: ${this.isRecord}`, {
        fontFamily: "BF",
        fontSize: 22,
        fontWeight: "bold",
        fill: ["#ffffff"],
        stroke: "#000",
        strokeThickness: 5,
      });
    },
    moveGround() {
      this.ground.tilingSpriteGround.tilePosition.x -= 1.1;
    },
    setPositionRecordText() {
      this.recordText.text.position.set(this.recordText.x, this.recordText.y);
    },
    downloadTexture() {
      this.gameOver.texture = PIXI.Texture.from(gameOverImg);
      this.bird.texture = PIXI.Texture.from(gshotBird);
      this.buttonStart.texture = PIXI.Texture.from(buttonStartURL);
      this.ground.groundTexture = PIXI.Texture.from(groundURL);
    },
    createdSprites() {
      this.gameOver.sprite = PIXI.Sprite.from(this.gameOver.texture);
      this.bird.sprite = PIXI.Sprite.from(this.bird.texture);
      this.buttonStart.sprite = PIXI.Sprite.from(this.buttonStart.texture);
      this.ground.tilingSpriteGround = new PIXI.TilingSprite(
        this.ground.groundTexture,
        393,
        123
      );
    },
    addSpriteInContainer() {
      this.logoContainer.container.addChild(this.gameOver.sprite);
      this.logoContainer.container.addChild(this.bird.sprite);
    },
    addSpriteInGame() {
      this.app.game.stage.addChild(this.ground.tilingSpriteGround);
      this.app.game.stage.addChild(this.buttonStart.sprite);
    },
    setPositionSpritesInContainer() {
      this.gameOver.sprite.position.set(this.gameOver.x, this.gameOver.y);
      this.bird.sprite.position.set(this.bird.x, this.bird.y);
    },
    setPositionSpritesInGame() {
      this.ground.tilingSpriteGround.position.set(this.ground.x, this.ground.y);
      this.buttonStart.sprite.position.set(
        this.buttonStart.x,
        this.buttonStart.y
      );
      this.bird.sprite.position.set(this.bird.x, this.bird.y);
    },
    setCursorPointer() {
      const pointerIcon = `url(${pointer}),auto`;
      this.app.game.renderer.plugins.interaction.cursorStyles.pointer =
        pointerIcon;
    },

    listener() {
      window.addEventListener("keydown", (e) => {
        if (e.key === "Enter") {
          this.startGame();
        }
      });

      window.addEventListener("touchend", () => {
        if(this.$route.name=="gameover"){
          this.startGame();
        }
      });
    },
  },
};
</script>

<style lang="scss">
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

.game-over {
  width: 340px;
  height: 600px;
  flex-grow: 1;
  background-color: aqua;
  // background-image: url("./../assetgame/img/bg.png");
  background-position: center center;
  background-repeat: no-repeat;
  background-size: contain;
  margin-top: 50px;
  margin-left: 40%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.record {
  text-transform: uppercase;
  font-size: 20px;
  font-weight: bold;
  position: absolute;
  top: 0;
  left: 0;
}

@media (max-width: 600px) {
  .game-over {
    flex-grow: 1;
    width: 330px;
    height: 600px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 5% 5% 5% 5%;
  }
}
</style>
