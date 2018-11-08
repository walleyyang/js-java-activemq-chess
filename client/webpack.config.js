const HtmlWebpackPlugin = require('html-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const OptimizeCSSAssetsPlugin = require('optimize-css-assets-webpack-plugin')
const Path = require('path')
const UglifyJsPlugin = require('uglifyjs-webpack-plugin')
const Webpack = require('webpack')

module.exports = {
  entry: {
    vendor: './src/vendor.module.js',
    app: './src/app.module.js'
  },
  output: {
    path: Path.resolve(__dirname, './dist'),
    filename: '[name].bundle.js'
  },
  optimization: {
    minimizer: [
      new UglifyJsPlugin({
        cache: true,
        parallel: true
      }),
      new OptimizeCSSAssetsPlugin({})
    ]
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env']
          }
        }
      },
      {
        test: /\.css$/,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader'
        ]
      },
      {
        test: /\.(jpe?g|png|svg)$/i,
        use: [
          'file-loader?name=[name].[ext]&publicPath=images/&outputPath=images/',
          'image-webpack-loader'
        ]
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      minify: {
        collapseWhitespace: true
      },
      template: './src/index.html'
    }),
    new MiniCssExtractPlugin({
      filename: '[name].css',
      chunkFilename: '[id].css'
    }),
    new Webpack.IgnorePlugin(/^pg-native$/) // Ignore: Can't resolve 'pg-native'
  ],
  devServer: {
    port: 9000
  },
  watch: true,
  node: {
    net: 'empty',
    tls: 'empty',
    dns: 'empty',
    fs: 'empty'
  }
}
