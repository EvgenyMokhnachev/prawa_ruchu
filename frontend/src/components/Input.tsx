import React from 'react';

interface Props {
    label?: string
    value?: string|number|null;
    onChange?: (value: string) => void,
    style?: React.CSSProperties
}

export default function Input(props: Props) {
    const handleOnKeyUpInput = (e: any) => {
        props.onChange && props.onChange(e.currentTarget.value);
    }

    return (
        <div style={{
            display: 'inline-flex',
            flexDirection: 'column',
            alignItems: 'flex-start',
            ...(props.style || {})
        }}>
            {props.label && (
                <div>
                    {props.label}
                </div>
            )}

            <input style={{
                border: '2px solid black',
                borderRadius: '3px',
                ...(props.style?.width ? {width: props.style?.width} : {})
            }}
                   value={(props.value === null || props.value === undefined ? '' : props.value) + ''}
                   onChange={handleOnKeyUpInput}
            />
        </div>
    );
}
