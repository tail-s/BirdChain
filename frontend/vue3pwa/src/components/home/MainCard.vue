<template>
  <div class="nft">
    <div class="main">
      <img class="tokenImage" :src="product.img" alt="NFT" />
      <h2>{{ product.name }}</h2>
      <p class="description">우리의 도전</p>

      <div class="creator">
        <div class="wrapper">
          <img :src="product.img" alt="Creator" />
        </div>
        <p><ins>Owner of </ins>{{ product.owner }}</p>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "MainCard",
  props: {
    product: {
      id: String,
      img: String,
      owner: String,
    },
  },
};
</script>
<style lang="scss">
code {
  font-family: source-code-pro, Menlo, Monaco, Consolas, "Courier New",
    monospace;
}

.nft {
  user-select: none;
  max-width: 280px;
  margin: 5rem auto;
  border: 1px solid #ffffff22;
  background-color: #ffffff;
  box-shadow: 0 7px 5px 5px #00000088;
  border-radius: 0.7rem;
  backdrop-filter: blur(7px);
  -webkit-backdrop-filter: blur(7px);
  overflow: hidden;
  transition: 0.5s all;
  hr {
    width: 100%;
    border: none;
    border-bottom: 1px solid #88888855;
    margin-top: 0;
  }
  ins {
    text-decoration: none;
  }
  .main {
    display: flex;
    flex-direction: column;
    padding: 1rem;
    .tokenImage {
      border-radius: 0.5rem;
      max-width: 100%;
      height: 250px;
      object-fit: cover;
    }
    .description {
      margin: 0.5rem 0;
      color: #a89ec9;
    }
    .tokenInfo {
      display: flex;
      justify-content: space-between;
      align-items: center;
      .price {
        display: flex;
        align-items: center;
        color: #ee83e5;
        font-weight: 700;
        ins {
          margin-left: -0.3rem;
          margin-right: 0.5rem;
        }
      }
      .duration {
        display: flex;
        align-items: center;
        color: #a89ec9;
        margin-right: 0.2rem;
        ins {
          margin: 0.5rem;
          margin-bottom: 0.4rem;
        }
      }
    }
    .creator {
      display: flex;
      align-items: center;
      margin-top: 0.2rem;
      margin-bottom: -0.3rem;
      ins {
        color: #a89ec9;
        text-decoration: none;
      }
      .wrapper {
        display: flex;
        align-items: center;
        border: 1px solid #ffffff22;
        padding: 0.3rem;
        margin: 0;
        margin-right: 0.5rem;
        border-radius: 100%;
        box-shadow: inset 0 0 0 4px #000000aa;
        img {
          border-radius: 100%;
          border: 1px solid #ffffff22;
          width: 2rem;
          height: 2rem;
          object-fit: cover;
          margin: 0;
        }
      }
    }
  }
  ::before {
    position: fixed;
    content: "";
    box-shadow: 0 0 100px 40px #ffffff08;
    top: -10%;
    left: -100%;
    transform: rotate(-45deg);
    height: 60rem;
    transition: 0.7s all;
  }
  &:hover {
    border: 1px solid #ffffff44;
    box-shadow: 0 7px 50px 10px #000000aa;
    transform: scale(1.015);
    filter: brightness(1.3);
    ::before {
      filter: brightness(0.5);
      top: -100%;
      left: 200%;
    }
  }
}

@media (max-width: 600px) {
  .nft {
    user-select: none;
    max-width: 280px;
    margin: 5rem auto;
    border: 1px solid #ffffff22;
    background-color: #ffffff;
    box-shadow: 0 7px 5px 5px #00000088;
    border-radius: 0.7rem;
    backdrop-filter: blur(7px);
    -webkit-backdrop-filter: blur(7px);
    overflow: hidden;
    transition: 0.5s all;
    hr {
      width: 100%;
      border: none;
      border-bottom: 1px solid #88888855;
      margin-top: 0;
    }
    ins {
      text-decoration: none;
    }
    .main {
      display: flex;
      flex-direction: column;
      padding: 1rem;
      .tokenImage {
        border-radius: 0.5rem;
        width: 100%;
        height: 250px;
        object-fit: cover;
      }
      .description {
        margin: 0.5rem 0;
        color: #a89ec9;
      }
      .tokenInfo {
        display: flex;
        justify-content: space-between;
        align-items: center;
        .price {
          display: flex;
          align-items: center;
          color: #ee83e5;
          font-weight: 700;
          ins {
            margin-left: -0.3rem;
            margin-right: 0.5rem;
          }
        }
        .duration {
          display: flex;
          align-items: center;
          color: #a89ec9;
          margin-right: 0.2rem;
          ins {
            margin: 0.5rem;
            margin-bottom: 0.4rem;
          }
        }
      }
      .creator {
        display: flex;
        align-items: center;
        margin-top: 0.2rem;
        margin-bottom: -0.3rem;
        ins {
          color: #a89ec9;
          text-decoration: none;
        }
        .wrapper {
          display: flex;
          align-items: center;
          border: 1px solid #ffffff22;
          padding: 0.3rem;
          margin: 0;
          margin-right: 0.5rem;
          border-radius: 100%;
          box-shadow: inset 0 0 0 4px #000000aa;
          img {
            border-radius: 100%;
            border: 1px solid #ffffff22;
            width: 2rem;
            height: 2rem;
            object-fit: cover;
            margin: 0;
          }
        }
      }
    }
    ::before {
      position: fixed;
      content: "";
      box-shadow: 0 0 100px 40px #ffffff08;
      top: -10%;
      left: -100%;
      transform: rotate(-45deg);
      height: 60rem;
      transition: 0.7s all;
    }
    &:hover {
      border: 1px solid #ffffff44;
      box-shadow: 0 7px 50px 10px #000000aa;
      transform: scale(1.015);
      filter: brightness(1.3);
      ::before {
        filter: brightness(0.5);
        top: -100%;
        left: 200%;
      }
    }
  }
}
</style>
