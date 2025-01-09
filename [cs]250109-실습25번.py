from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import confusion_matrix, classification_report
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# 데이터 로드
data = pd.read_csv('customer_churn.csv')

# 데이터 확인
print(data.head())

# 결측값 확인
print(data.isnull().sum())

# x and y 분리
x = data.drop('churn', axis=1)
y = data['churn']

# 범주형 변수 인코딩
x = pd.get_dummies(x, drop_first=True)

# 훈련, 테스트 데이터 분할
x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=42)

# 데이터 표준화
scaler = StandardScaler()
x_train = scaler.fit_transform(x_train)
x_test = scaler.transform(x_test)
