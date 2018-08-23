/** 定义搜索控制器 */
app.controller("searchController" ,function ($scope, baseService) {

    /** 定义搜索参数对象 */
    $scope.searchParam = {keywords : '', category : '',  brand : '',
        spec : {}};

    // 定义搜索方法
    $scope.search = function(){
        // 发送异步请求
        baseService.sendPost("/Search", $scope.searchParam)
            .then(function(response){
                // 获取响应数据(搜索的结果) {"rows" : [{},{}], "categoryList":["",""]}
                $scope.resultMap = response.data;
            });
    };

    /** 添加搜索选项方法 */
    $scope.addSearchItem = function(key, value){
        // 判断是否为商品分类、品牌
        if (key == "category" || key == "brand"){
            $scope.searchParam[key] = value;
        }else{ // 规格选项
            $scope.searchParam.spec[key] = value;
        }
        /** 执行搜索 */
        $scope.search();
    };

    /** 删除搜索选项方法 */
    $scope.removeSearchItem = function(key){
        // 判断是否为商品分类、品牌
        if (key == "category" || key == "brand"){
            $scope.searchParam[key] = '';
        }else{ // 规格选项 {key : value, key : value}
           delete $scope.searchParam.spec[key];
        }
        /** 执行搜索 */
        $scope.search();
    };


});
