/** 定义控制器 */
app.controller('itemController', function ($scope,$http) {

    // 购买数量加一减一
    $scope.addNum = function (num) {
        $scope.num += num;
        // 判断购买数量不能小于1
        if ($scope.num < 1){
            $scope.num = 1;
        }
    };


    /** 记录用户选择的规格选项 */
    $scope.specItems = {};

    // 为用户选择的规格选项绑定点击事件
    $scope.selectSpec = function (key, value) {
        $scope.specItems[key] = value;
        /** 查找对应的SKU商品 */
        searchSku();
    };

    // 判断规格选项是否选中
    $scope.isSelected = function(key, value){
        return $scope.specItems[key] == value;
    };

    // 加载默认的SKU商品数据
    $scope.loadSku = function(){
        // 到skuList数组中取第一个元素[{},{}]
        $scope.sku = skuList[0];
        /** 获取SKU商品选择的选项规格 */
        $scope.specItems = JSON.parse($scope.sku.spec);
    };

    // 根据用户选择的规格选项查询对应的SKU商品
    var searchSku = function(){
        // 循环SKU数组
        for (var i = 0; i < skuList.length; i++){
            // 取一个
            var obj = skuList[i];
            if (obj.spec == JSON.stringify($scope.specItems)){
                $scope.sku = obj;
                break;
            }
        }
    };

    // 为加入购物车的按钮绑定点击事件
    $scope.addToCart = function(){

        $http.get("http://cart.pinyougou.com/cart/addCart?itemId="+$scope.sku.id+"&num="+$scope.num,{"withCredentials":true})
            .then(function (response) {
                if (response.data){
                    alert("请求成功!");
                    location.href='http://cart.pinyougou.com/cart.html';
                }else{
                    alert("请求失败了!wss");
                }
            })

    };

});