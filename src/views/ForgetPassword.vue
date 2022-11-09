<template>
  <div class="forgotpwd">
    <section class="form_title">
      <div class="manage_title">
        <el-form
          :model="forgotPass"
          :rules="rules"
          ref="forgotPassForm"
          label-width="80px"
          class="registerForm"
          hide-required-asterisk
          
        >
          <div class="forgot_title">
            <h3>forget password</h3>
          </div>
          <el-form-item label="email" prop="email">
            <el-input
              type="email"
              v-model="forgotPass.email"
              prefix-icon="el-icon-message"
              placeholder="type in email"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="password" prop="password">
            <el-input
              :type="pwdType"
              v-model="forgotPass.password"
              prefix-icon="el-icon-lock"
              placeholder="type in password"
            >
              <i
        
                title="show or hide password"
                :class="showPwd"
                :style="{ color: '#409eff', cursor: 'pointer' }"
                @click="togglePwd"
              >
              </i>
            </el-input>
          </el-form-item>
          <el-form-item label="confirm password" prop="password2">
            <el-input
              :type="pwdType"
              v-model="forgotPass.password2"
              prefix-icon="el-icon-lock"
              placeholder="confirm password"
            >
              <i
     
                title="show or hide password"
                :class="showPwd"
                :style="{ color: '#409eff', cursor: 'pointer' }"
                @click="togglePwd"
              >
              </i>
            </el-input>
          </el-form-item>
          <el-form-item label="verification code" prop="verificationCode">
            <div class="vertify">
              <el-input
                prefix-icon="el-icon-key"
                v-model="forgotPass.verificationCode"
                placeholder="verification code"
                auto-complete="off"
              ></el-input>
              <el-button
                type="primary"
                plain
                @click="getCode('forgotPassForm')"
                :disabled="isDisabled"
                class="emailcode"
              >
                {{ btnMsg }}
              </el-button>
            </div>
          </el-form-item>
          <div class="forgot_button">
            <el-button type="primary" @click="forgetPass('forgotPassForm')" class="confirm"
              >submit</el-button
            >
          </div>
          <div class="tip_area">
            <router-link to="/moviehub/loginpage">go to login</router-link>
            <p>If there is no verification code,check spam email </p>
          </div>
        </el-form>
      </div>
    </section>
  </div>
  <HubIcon/>
</template>

<script>
import request from "@/utils/RequestFile.js"
import HubIcon from "@/components/HubIcon.vue"
export default {
    name: "ForgotPassword",
    data() {
        const validatePass2 = (rule, value, callback) => {
            if (value !== this.forgotPass.password) {
                callback(new Error("input same password!!!"));
            }
            else {
                callback();
            }
        };
        return {
            pwdType: "password",
            isShowPwd: false,
            btnMsg: "get code",
            isDisabled: false,
            time: 30,
            forgotPass: {
                email: "",
                password: "",
                // password2: "",
                verificationCode: "",
            },
            rules: {
                email: [
                    { required: true, message: "email cannot be blank", trigger: "blur" },
                    { type: "email", message: "input correct email", trigger: "change" },
                ],
                password: [
                    { required: true, message: "password cannot be blank", trigger: "blur" },
                    {
                        min: 5,
                        max: 16,
                        message: "length of password should be >5, <16",
                        trigger: "blur",
                    },
                ],
                password2: [
                    { required: true, message: "confirm password cannot be blank", trigger: "blur" },
                    {
                        min: 5,
                        max: 16,
                        message: "length of confirm password should be >5, <16",
                        trigger: "blur",
                    },
                    { validator: validatePass2, trigger: "blur" },
                ],
            },
        };
    },
    computed: {
        showPwd() {
            return this.isShowPwd ? "third-icon-alieye" : "third-icon-alino_eye";
        },
    },
    methods: {
        togglePwd() {
            this.pwdType === "password"
                ? (this.pwdType = "")
                : (this.pwdType = "password");
            this.isShowPwd = !this.isShowPwd;
        },
        // verification code
        getCode(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request
                        .post("/user/forgotPassword/email", {
                            email: this.forgotPass.email,
                 
                    })
                        .then((res) => {
                        // console.log(res)
                        if (res.status === 200) {
                          this.$message({
                                message: "email is sent",
                                type: "success",
                            });
                            
                            this.isDisabled = true;
                            let interval = setInterval(() => {
                                this.btnMsg = "" + this.time + "seconds";
                                --this.time;
                                if (this.time < 0) {
                                    this.btnMsg = "get code";
                                    this.time = 30;
                                    this.isDisabled = false;
                                    clearInterval(interval);
                                }
                       }, 1000);
                        }
                    }).catch((err) => {
                      console.log(err)
                      this.$message({
                      message: "incorrect password or email!",
                      type: "error",
                     })
                    });
                }else{
                  this.$message({
                     message: "complete the form first!",
                     type: "error",
                   })
                }
            });
        },
        // change password
        forgetPass(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request
                        .post("/user/forgotPassword", this.forgotPass)
                        .then((res) => {
                        // console.log(res)
                        if (res.status === 200) {
                            this.$message({
                                message: res.data.message,
                                type: "success",
                            });
                            setTimeout(() => {
                                history.pushState({}, "", "/moviehub/loginpage"); //loginpage
                                location.reload();
                            }, 1000);
                        }
                        else {
                            this.$message({
                                message: res.data.message,
                                type: "error",
                            });
                        }
                    })
                        .catch((err) => {
                        this.$message({
                            message: "system error!",
                            type: "error",
                        });
                        console.log("Error", err);
                    });
                }else{
                  this.$message({
                      message: "complete the form first!",
                      type: "error",
                   })
                }
            });
        },
    },
    components: { HubIcon }
}
</script>

<style>
   body {
background-image:url('https://wallpapercave.com/dwp2x/wp11089675.jpg');

}
.forgotpwd {
  position: relative;
  width: 100%;
  height: 100%;

  background-size: 100% 100%;
}
.form_title {
  width: 370px;
  height: 210px;
  position: absolute;
  top: 5%;
  left: 34%;
  padding: 25px;
  border-radius: 5px;
  text-align: center;
}
.form_title .manage_title .title {
  font-family: 'Microsoft YaHei';
  font-weight: bold;
  font-size: 26px;
  color: orange;
}
.forgot_title {
  margin-bottom: 20px;
  text-align: center;
  color: orange;
}
.registerForm {
  margin-top: 20px;
  background-color: #fff;
  padding: 20px 40px 20px 20px;
  border-radius: 5px;
  box-shadow: 0px 5px 10px #cccc;
}
.vertify {
  display: flex;
  justify-content: space-between;
}
.vertify >>> .emailcode {
  margin-left: 10px;
  font-size: 10px;
}
.forgot_button {
  padding-left: 80px;
}
.confirm {
  width: 100%;
}
.tip_area {
  text-align: left;
  font-size: 12px;
  color: orange;
  margin-top: 20px;
}
.tip_area a {
  color: orange;
}
.tip_area p {
  margin-top: 15px;
  color: gray;
}
</style>