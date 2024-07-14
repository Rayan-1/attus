<template>
    <div class="container-login">

        <v-container cols="12" lg="4" md="6" sm="8" class="form-container">
            <v-col cols="12" lg="56" md="6" sm="8">
                <v-card>
                    <v-form v-model="valid">
                        <v-container>
                            <v-card-title>Esqueci Senha</v-card-title>

                            <v-card-text>
                                <v-form ref="form" v-model="valid">
                                    <v-container>
                                        <v-row>
                                            <v-col cols="12">
                                                <v-text-field v-model="usuario.email" :counter="10" :rules="emailRules"
                                                    label="E-mail" hide-details required></v-text-field>
                                            </v-col>

                                            <v-col cols="12">
                                                <v-text-field v-model="usuario.novaSenha" :counter="10" :rules="senhaRules"
                                                    label="Nova Senha" hide-details required></v-text-field>
                                            </v-col>

                                            <v-col cols="12">
                                                <v-btn variant="flat"
                                                        prepend-icon="mdi-content-save"
                                                        color="blue"
                                                        width="100%"
                                                        @click="enviar">
                                                Entrar</v-btn>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </v-form>
                            </v-card-text>
                        </v-container>
                    </v-form>
                </v-card>
            </v-col>
        </v-container>
    </div>
</template>

<script>
import router from '@/router';
import axios  from 'axios';

export default {
    name: 'MudarSenhaView',
    data() {
        return {
            usuario: {
                email: '',
                senha: '',
            },
            valid: false,
            emailRules: [
                v =>!!v || 'E-mail obrigatório',
                v => /.+@.+\..+/.test(v) || 'E-mail precisa ser válido',
            ],
            senhaRules: [
                v =>!!v || 'Senha obrigatória',
                v => v.length >= 4 || 'A senha precisa ter no mínimo 4 caracteres',
            ],  
        }
    },
    methods: {
        async login() {
            if(this.$refs.form.validate()){
                if(this.usuario.email == "" || this.usuario.novaSenha == "" ){
                    return;
                }
                await axios.put("http://localhost:8080/api/login",null ,{
                    params:{
                        email : this.usuario.email,
                        novaSenha : this.usuario.novaSenha
                    }
                })
                .then(response => {
                    if(response.status === 200){
                        console.log("sucesso")
                        router.push("/sucesso")
                    }
                })
                .catch((error) => {
                    console.log(error);
                });
                console.log(this.usuario)
            }
            return 
        },
        enviar() {
            this.login();

        },
    
    }
}
</script>

<style>
.container-login {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: black;
    background-size: cover;
    width: 100%;
    height: 100vh;
}


.form-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
</style>