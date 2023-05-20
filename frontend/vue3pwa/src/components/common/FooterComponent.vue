<template>
  <div class="footer">
    <div class="footerTextLink">About US | Contact Us</div>
    <br />
    <div class="footerText">Bird Chain Project</div>
    <div class="footerText">Team 둥지</div>
  </div>
</template>

<script>
export default {
  name: "FooterComponent",
};
</script>
<style>
.footer {
  margin-bottom: 50px;
  border-top: solid #6f6f6f;
  width: 100%;
}
.footerTextLink {
  margin-top: 20px;
  text-align: left;
  margin-left: 100px;
  font-family: "Roboto";
  font-style: normal;
  font-weight: 400;
  font-size: 15px;
  display: flex;
  align-items: center;
  color: #635959;
}
.footerText {
  text-align: left;
  margin-left: 100px;
  font-style: bold;
  font-family: "Roboto";
  font-style: normal;
  font-weight: 400;
  font-size: 15px;
}
</style>
