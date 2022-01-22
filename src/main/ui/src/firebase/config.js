import { initializeApp } from 'firebase/app'
import { getAuth } from 'firebase/auth'

const firebaseConfig = {
    apiKey: "AIzaSyDFTDljeRfq1HgZC5LxgFSX1bZwLG2DvzQ",
    authDomain: "webdev2021-61701.firebaseapp.com",
    projectId: "webdev2021-61701",
    storageBucket: "webdev2021-61701.appspot.com",
    messagingSenderId: "982645710338",
    appId: "1:982645710338:web:f3ce18bb8d2635d324511b"
  };
  

// init firebase
initializeApp(firebaseConfig)

// init firebase auth
const auth = getAuth()

export { auth }