<template>
    <div class="main-map" ref="map">
    </div>  
  </template>
  
  <script>
  import OlLayerTile from 'ol/layer/Tile.js';
  import OlView from 'ol/View.js';
  import OlMap from 'ol/Map.js';
  import OSM from 'ol/source/OSM';
  import {fromLonLat, toLonLat} from 'ol/proj.js';
  import {defaults} from 'ol/control.js';
  import axios from 'axios';
  import Geocoder from "ol-geocoder";
  import OlVectorSource from 'ol/source/Vector.js';
  import OlVectorLayer from 'ol/layer/Vector.js';
  const EPSG_3857 = 'EPSG:3857';
  import OlFeature from 'ol/Feature.js';
  import OlPoint from 'ol/geom/Point';
  import OlStyle from 'ol/style/Style.js';
  import OlIcon from 'ol/style/Icon.js'

  export default {
    
    name: 'MainMap',
    data() {
      return {
        olMap: undefined,
        iconsSource: undefined
      }
    },
    async mounted() {
      const vectorSource = new OlVectorSource(EPSG_3857);
      const vectorLayer = new OlVectorLayer({
        source: vectorSource
      })
      this.olMap = new OlMap({
        target: this.$refs.map,
        controls : defaults({
            attribution : false,
            zoom : false,
            rotate: false,
        }),
        layers: [
            new OlLayerTile({
              source: new OSM()
            }),
            vectorLayer
        ],
        view: new OlView({
          center: fromLonLat([127.1388684, 37.4449168]), // 경기도 성남
          zoom: 10,
          projection: EPSG_3857 // 생략 가능
        })
      });
      
      await this.$store.dispatch('setReviews', this);

      this.olMap.on('click', async (e) => {
        console.log(toLonLat(e.coordinate));
        // await addUiAddress();
        drawMapIcon();
        
        // async function addUiAddress() {
        //   const lonLatArr = toLonLat(e.coordinate)
        //   const lon = lonLatArr[0]
        //   const lat = lonLatArr[1]
        //   const addressInfo = await this.getAddress(lon, lat)
        //   this.setUiAddress(addressInfo.data.display_name);
        // }

        function drawMapIcon() {
          vectorSource.clear();
          geocoder.getSource().clear();
          const feature = new OlFeature({
            geometry: new OlPoint(e.coordinate)
          })
          feature.setStyle(new OlStyle({
            image: new OlIcon({
              scale: 0.7,
              src: '//cdn.rawgit.com/jonataswalker/map-utils/master/images/marker.png'
            })
          }))
          vectorSource.addFeature(feature);    
        }
      })

      const geocoder = new Geocoder('nominatim', {
        provider: 'osm',
        lang: 'kr',
        placeholder: '주소 검색',
        limit: 5, // 자동 완성 결과 최대 개수
        autoComplete: true,
        keepOpen: true
      });
      this.olMap.addControl(geocoder);

      geocoder.on('addresschosen', (evt) => {
        this.setUiAddress(evt.address.details.name);
      });
    },
    methods: {
      drawFeatures() {
            if (this.iconsSource)
                this.iconsSource.clear();

            this.iconsSource = new OlVectorSource(EPSG_3857);
            const iconsLayer = new OlVectorLayer({
                source: this.iconsSource
            });
            const style = new OlStyle({
                image: new OlIcon({
                    scale: 0.8,
                    src: '//cdn.rawgit.com/jonataswalker/map-utils/master/images/marker.png'
                })
            });
            const features = this.reviews.map(review => {
                const point = this.coordi4326To3857([review.lon, review.lat]);
                const feature = new OlFeature({
                    geometry: new OlPoint(point)
                });
                feature.set('title', review.title);
                feature.set('grade', review.grade);
                feature.set('address', review.address);
                feature.set('content', review.content);
                feature.set('reviewId', review.id);
                feature.setStyle(style);

                return feature;
            })
            this.iconsSource.addFeatures(features);

            this.olMap.addLayer(iconsLayer);
        },
      // getAddress (lon, lat) {
      //   return axios.get('https://nominatim.openstreetmap.org/reverse',
      //     {
      //       params: {
      //         format: 'json',
      //         lon: lon,
      //         lat: lat
      //       }
      //     })
      // },
      setUiAddress(str) {
        this.$root.$refs.sideBar.address = str.split(', ').reverse().join(' ');
      },
      getReview() {
        axios.get("/api/review/getReviews").then((res) => {
          console.log(res.data);
        })
      },
    }
  
  }
  </script>
  
  <style lang="scss" scoped>
  .main-map {
    width: 100%;
    height: 100%;

    ::v-deep.ol-geocoder {
      position: absolute;
      right: 0;
      padding: 10px;

      button {
        display: none;
      }

      input::placeholder {
        color: white;
        opacity: 0.7;
      }

      input, ul {
        border-style: none;
        width: 200px;
        background-color: rgba(0, 0, 0, 0.5);
        border-radius: 5px;
        border-color: unset;
        padding: 0 5px;
        color: white;
      }

      ul {
        margin-top: 5px;
        padding: 0;
        list-style: none;

        li:hover {
          background-color: rgba(0, 0, 0, 0.3);
        }

        li {
          padding: 5px 10px;
          font-size: 13px;

          a {
            text-decoration: none;

            .gcd-road {
              color: white;
            }
          }
        }
      }
    }
  }

  
  </style>