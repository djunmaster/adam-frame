<?xml version="1.0" encoding="UTF-8"?>
<w:wordDocument
        xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
        xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint">

    <w:body>
        <w:p>
            <w:pPr>
                <w:jc w:val="center"/>
            </w:pPr>
            <w:r>
                <w:rPr>
                    <w:b w:val="on"/>
                    <w:sz w:val="36"/>
                </w:rPr>
                <w:t>用户信息报告</w:t>
            </w:r>
        </w:p>

        <w:p/>

        <w:tbl>
            <w:tblPr>
                <w:tblW w:w="5000" w:type="pct"/>
                <w:tblBorders>
                    <w:top w:val="single" w:sz="4" wx:bdrwidth="10" w:space="0" w:color="auto"/>
                    <w:left w:val="single" w:sz="4" wx:bdrwidth="10" w:space="0" w:color="auto"/>
                    <w:bottom w:val="single" w:sz="4" wx:bdrwidth="10" w:space="0" w:color="auto"/>
                    <w:right w:val="single" w:sz="4" wx:bdrwidth="10" w:space="0" w:color="auto"/>
                    <w:insideH w:val="single" w:sz="4" wx:bdrwidth="10" w:space="0" w:color="auto"/>
                    <w:insideV w:val="single" w:sz="4" wx:bdrwidth="10" w:space="0" w:color="auto"/>
                </w:tblBorders>
            </w:tblPr>
            <w:tr>
                <w:tc>
                    <w:p><w:r><w:t>用户ID</w:t></w:r></w:p>
                </w:tc>
                <w:tc>
                    <w:p><w:r><w:t>${userId}</w:t></w:r></w:p>
                </w:tc>
            </w:tr>
            <w:tr>
                <w:tc>
                    <w:p><w:r><w:t>用户名</w:t></w:r></w:p>
                </w:tc>
                <w:tc>
                    <w:p><w:r><w:t>${username}</w:t></w:r></w:p>
                </w:tc>
            </w:tr>
            <w:tr>
                <w:tc>
                    <w:p><w:r><w:t>注册日期</w:t></w:r></w:p>
                </w:tc>
                <w:tc>
                    <w:p><w:r><w:t>${registerDate}</w:t></w:r></w:p>
                </w:tc>
            </w:tr>
        </w:tbl>
    </w:body>
</w:wordDocument>
