import axios from 'axios'

const request2 = axios.create({
  baseURL: "https://imdb-api.com/en/API/AdvancedSearch/k_td4a1cav/",
  timeout: 100000
})

export default request2