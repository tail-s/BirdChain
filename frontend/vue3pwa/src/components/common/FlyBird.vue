<template >
  <div>
    <div class="bird-container bird-container--one">
      <div class="bird bird--one"></div>
    </div>
    
    <div class="bird-container bird-container--two" style="margin-top: 50px;">
      <div class="bird bird--two"></div>
    </div>
    
    <div class="bird-container bird-container--three">
      <div class="bird bird--three"></div>
    </div>
    
    <div class="bird-container bird-container--four">
      <div class="bird bird--four"></div>
    </div>
      <div class="bird-container bird-container--five" style="margin-top: 50px;">
        <div class="bird bird--five"></div>
      </div>
      
      <div class="bird-container bird-container--six" >
        <div class="bird bird--six"></div>
      </div>
      
      <div class="bird-container bird-container--seven" style="margin-top: 50px;">
        <div class="bird bird--seven"></div>
      </div>
      
      <div class="bird-container bird-container--eight" style="margin-top: 50px;">
        <div class="bird bird--eight"></div>
      </div>
  </div>
</template>

<script>
export default {
  name : "FlyBird",
}
</script>
<style lang="scss">
  @import url('https://fonts.googleapis.com/css?family=Arima+Madurai:300');

*,
*::before,
*::after {
	box-sizing: border-box;
}



.bird {
	background-image: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/174479/bird-cells-new.svg);
	background-size: auto 100%;
	width: 88px;
	height: 125px;
	will-change: background-position;
	
	animation-name: fly-cycle;
	animation-timing-function: steps(10);
	animation-iteration-count: infinite;

	&--one {
		animation-duration: 1s;
		animation-delay: -0.5s;		
	}
	
	&--two {
		animation-duration: 0.9s;
		animation-delay: -0.75s;
	}
	
	&--three {
		animation-duration: 1.25s;
		animation-delay: -0.25s;
	}
	
	&--four {
		animation-duration: 1.1s;
		animation-delay: -0.5s;
	}

  &--five {
		animation-duration: 1.5s;
		animation-delay: -0.5s;		
	}
	
	&--six {
		animation-duration: 1.75s;
		animation-delay: -0.75s;
	}
	
	&--seven {
		animation-duration: 2s;
		animation-delay: -0.4s;
	}
	
	&--eight {
		animation-duration: 2.25s;
		animation-delay: -0.5s;
	}

}

.bird-container {
	position: absolute;
	top: 20%;
	left: -10%;
	transform: scale(0) translateX(-10vw);
	will-change: transform;
	
	animation-name: fly-right-one;
	animation-timing-function: linear;
	animation-iteration-count: infinite;
	
	&--one {
		animation-duration: 15s;
		animation-delay: 0;
	}
	
	&--two {
		animation-duration: 16s;
		animation-delay: 1s;
	}
	
	&--three {
		animation-duration: 14.6s;
		animation-delay: 9.5s;
	}
	
	&--four {
		animation-duration: 16s;
		animation-delay: 10.25s;
	}

  &--five {
		animation-duration: 17s;
		animation-delay: 3s;		
	}
	
	&--six {
		animation-duration: 15s;
		animation-delay: 5s;
	}
	
	&--seven {
		animation-duration: 14s;
		animation-delay: 7s;
	}
	
	&--eight {
		animation-duration: 17s;
		animation-delay: 11s;
	}
	
}

@keyframes fly-cycle {
	
	100% {
		background-position: -900px 0;
	}
	
}

@keyframes fly-right-one {
	
	0% {
		transform: scale(0.3) translateX(-10vw);
	}
	
	10% {
		transform: translateY(2vh) translateX(10vw) scale(0.4);
	}
	
	20% {
		transform: translateY(0vh) translateX(30vw) scale(0.5);
	}
	
	30% {
		transform: translateY(4vh) translateX(50vw) scale(0.6);
	}
	
	40% {
		transform: translateY(2vh) translateX(70vw) scale(0.6);
	}
	
	50% {
		transform: translateY(0vh) translateX(90vw) scale(0.6);
	}
	
	60% {
		transform: translateY(0vh) translateX(110vw) scale(0.6);
	}
	
	100% {
		transform: translateY(0vh) translateX(110vw) scale(0.6);
	}
	
}

@keyframes fly-right-two {
	
	0% {
		transform: translateY(-2vh) translateX(-10vw) scale(0.5);
	}
	
	10% {
		transform: translateY(0vh) translateX(10vw) scale(0.4);
	}
	
	20% {
		transform: translateY(-4vh) translateX(30vw) scale(0.6);
	}
	
	30% {
		transform: translateY(1vh) translateX(50vw) scale(0.45);
	}
	
	40% {
		transform: translateY(-2.5vh) translateX(70vw) scale(0.5);
	}
	
	50% {
		transform: translateY(0vh) translateX(90vw) scale(0.45);
	}
	
	51% {
		transform: translateY(0vh) translateX(110vw) scale(0.45);
	}
	
	100% {
		transform: translateY(0vh) translateX(110vw) scale(0.45);
	}
	
}
</style>